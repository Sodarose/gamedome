package com.game.gameserver.module.player.service;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.event.LoginEvent;
import com.game.gameserver.event.event.LogoutEvent;
import com.game.gameserver.module.achievement.service.AchievementService;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.email.service.EmailService;
import com.game.gameserver.module.equipment.service.EquipService;
import com.game.gameserver.module.friend.service.FriendService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.dao.PlayerDbService;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.entity.Role;
import com.game.gameserver.module.player.helper.PlayerHelper;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.module.scene.service.SceneService;
import com.game.gameserver.module.skill.service.SkillService;
import com.game.gameserver.module.task.service.TaskService;
import com.game.gameserver.module.user.module.User;
import com.game.gameserver.util.GameUUID;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/9 20:01
 */
@Service
public class PlayerService {
    private final static Logger logger = LoggerFactory
            .getLogger(PlayerService.class);
    public final static AttributeKey<Player> PLAYER_ENTITY_ATTRIBUTE_KEY
            = AttributeKey.newInstance("PLAYER_ENTITY_ATTRIBUTE_KEY");

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private PlayerDbService playerDbService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private PlayerDataService playerPropertyService;
    @Autowired
    private EquipService equipService;
    @Autowired
    private BackBagService backBagService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private AchievementService achievementService;

    /**
     * 获得账户角色列表
     *
     * @param user
     * @param channel
     * @return void
     */
    public void findRoleList(User user, Channel channel) {
        // 获得角色列表
        List<Role> roles = playerDbService.selectRoleList(user.getId());
        // 刷新角色列表
        user.getRoles().clear();
        roles.forEach(role -> {
            user.getRoles().add(role.getId());
        });
        // 返回信息
        NotificationHelper.notifyChannel(channel, PlayerHelper.buildRoleList(roles));
    }

    /**
     * 返回职业列表
     *
     * @param channel
     * @return void
     */
    public void findCareerList(Channel channel) {
        List<CareerConfig> careerConfigList = new ArrayList<>(StaticConfigManager
                .getInstance()
                .getCareerConfigMap().values());
        NotificationHelper.notifyChannel(channel, PlayerHelper.buildCareerList(careerConfigList));
    }


    /**
     * 登录角色
     *
     * @param playerId 角色Id
     * @param channel  角色通道信息
     * @return void
     */
    public void login(User user,long playerId, Channel channel) {
        // 判断账号是否拥有该角色
        if(!user.getRoles().contains(playerId)){
            NotificationHelper.notifyChannel(channel, "角色Id错误");
            return;
        }
        Player player = playerManager.getPlayer(playerId);
        // 当前角色未登录
        if (player == null) {
            // 从数据库中查找该角色
            PlayerEntity playerEntity = playerDbService.select(playerId);
            if (playerEntity == null) {
                NotificationHelper.notifyChannel(channel, "查询角色数据失败");
                return;
            }
            // 创建角色领域
            player = new Player(playerEntity);
            channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).set(player);
            player.setChannel(channel);
            initPlayer(player);
            // 放入缓存
            playerManager.putPlayer(playerId, player);
            // 角色登录成功
            NotificationHelper.notifyPlayer(player, MessageFormat.format("角色{0}登录成功",
                    player.getPlayerEntity().getName()));
            // 同步角色数据
            NotificationHelper.syncPlayer(player);
            // 发出登录事件
            LoginEvent loginEvent = new LoginEvent(player);
            EventBus.EVENT_BUS.fire(loginEvent);
        } else {
            // 踢出当前角色
            NotificationHelper.notifyPlayer(player, "你被人挤下线了");
            // 退出场景
            sceneService.exitScene(player);
            player.getChannel().attr(PLAYER_ENTITY_ATTRIBUTE_KEY).set(null);
            // 重新设置连接信息
            player.setChannel(channel);
            channel.attr(PLAYER_ENTITY_ATTRIBUTE_KEY).set(player);
            // 进入场景
            sceneService.initPlayerEntryScene(player);
            NotificationHelper.notifyPlayer(player, MessageFormat.format("角色{0}登录成功",
                    player.getPlayerEntity().getName()));
            // 同步角色数据
            NotificationHelper.syncPlayer(player);
            LoginEvent loginEvent = new LoginEvent(player);
            EventBus.EVENT_BUS.fire(loginEvent);
        }
    }

    /**
     * 展示角色
     *
     * @param player
     * @return void
     */
    public void showPlayer(Player player) {
        NotificationHelper.syncPlayer(player);
        NotificationHelper.notifyPlayer(player, PlayerHelper.buildplayer(player));
    }


    /**
     * 创建角色
     *
     * @param user
     * @param name
     * @param careerId
     * @param channel
     * @return void
     */
    public void createRole(User user, String name, int careerId, Channel channel) {
        // 判断职业是否正确
        if (StaticConfigManager.getInstance().getCareerConfigMap().get(careerId) == null) {
            NotificationHelper.notifyChannel(channel, "错误职业参数");
            return;
        }
        // 角色名是否已经存在
        if(playerDbService.count(name)>0){
            NotificationHelper.notifyChannel(channel, "该名字已经被使用了");
            return;
        }
        // 创建角色存储数据
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(GameUUID.getInstance().generate());
        playerEntity.setName(name);
        playerEntity.setLevel(1);
        playerEntity.setCareerId(careerId);
        playerEntity.setSceneId(1001);
        playerEntity.setExpr(0);
        playerEntity.setGolds(0);
        playerEntity.setBackBagCapacity(36);
        playerEntity.setWarehouseCapacity(64);
        playerEntity.setUserId(user.getId());

        // 存储角色Id
        user.getRoles().add(playerEntity.getId());

        // 存入数据库
        playerDbService.insert(playerEntity);

        // 创建角色背包 和 装备栏;
        equipService.createPlayerEquipBar(playerEntity.getId());
        backBagService.createBackBag(playerEntity.getId());
        NotificationHelper.notifyChannel(channel, MessageFormat.format("角色{0}创建成功",
                name));
    }

    private void initPlayer(Player player) {
        // 加载用户装备
        equipService.loadPlayerEquipBar(player);
        // 加载用户背包
        backBagService.loadPlayerBackBag(player);
        // 加载用户技能
        skillService.loadPlayerSkill(player);
        // 加载用户任务
        taskService.loadPlayerTask(player);
        // 加载用户成就
        achievementService.loadPlayerAchievement(player);
        // 加载用户邮件
        emailService.loadEmail(player);
        // 加载用户好友
        friendService.loadFriends(player);
        // 初始化角色战斗属性
        playerPropertyService.initPlayerBattle(player);
        // 进入场景
        sceneService.initPlayerEntryScene(player);
    }

    public void logout(Player player) {
        // 退出场景
        sceneService.exitScene(player);
        // 保存数据
        player.getChannel().attr(PLAYER_ENTITY_ATTRIBUTE_KEY).set(null);
        NotificationHelper.notifyPlayer(player,"退出当前角色");
        // 从缓存中剔除
        playerManager.removePlayer(player.getPlayerEntity().getId());
        // 发出角色退出事件
        LogoutEvent logoutEvent = new LogoutEvent(player);
        EventBus.EVENT_BUS.fire(logoutEvent);
    }

    public Player getPlayer(long playerId){
        return playerManager.getPlayer(playerId);
    }

    public Map<Long,Player> getAllPlayer(){
        return playerManager.getAllPlayer();
    }


}
