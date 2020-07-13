package com.game.gameserver.module.player.service;

import com.game.gameserver.common.config.CareerConfig;
import com.game.gameserver.common.config.StaticConfigManager;
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
    private PlayerPropertyService playerPropertyService;
    @Autowired
    private EquipService equipService;
    @Autowired
    private BackBagService backBagService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FriendService friendService;

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
        Player domain = playerManager.getPlayerDomain(playerId);
        // 当前角色未登录
        if (domain == null) {
            // 从数据库中查找该角色
            PlayerEntity player = playerDbService.select(playerId);
            if (player == null) {
                NotificationHelper.notifyChannel(channel, "查询角色数据失败");
                return;
            }
            // 创建角色领域
            domain = new Player(player);
            channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).set(domain);
            domain.setChannel(channel);
            initPlayer(domain);
            // 放入缓存
            playerManager.putPlayerDomain(playerId, domain);
            // 角色登录成功
            NotificationHelper.notifyPlayer(domain, MessageFormat.format("角色{0}登录成功",
                    domain.getPlayerEntity().getName()));
            // 同步角色数据
            NotificationHelper.syncPlayer(domain);
            // 发出登录事件

        } else {
            // 踢出当前角色
            NotificationHelper.notifyPlayer(domain, "你被人挤下线了");
            // 退出场景
            sceneService.exitScene(domain);
            domain.getChannel().attr(PLAYER_ENTITY_ATTRIBUTE_KEY).set(null);
            // 重新设置连接信息
            domain.setChannel(channel);
            channel.attr(PLAYER_ENTITY_ATTRIBUTE_KEY).set(domain);
            // 进入场景
            sceneService.initPlayerEntryScene(domain);
            NotificationHelper.notifyPlayer(domain, MessageFormat.format("角色{0}登录成功",
                    domain.getPlayerEntity().getName()));
            // 同步角色数据
            NotificationHelper.syncPlayer(domain);
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
        NotificationHelper.notifyPlayer(player, PlayerHelper.buildPlayerDomain(player));
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
        PlayerEntity player = new PlayerEntity();
        player.setId(GameUUID.getInstance().generate());
        player.setName(name);
        player.setLevel(1);
        player.setCareerId(careerId);
        player.setSceneId(1001);
        player.setExpr(0);
        player.setGolds(0);
        player.setBackBagCapacity(36);
        player.setWarehouseCapacity(64);
        player.setUserId(user.getId());

        // 存储角色Id
        user.getRoles().add(player.getId());

        // 存入数据库
        playerDbService.insert(player);
        NotificationHelper.notifyChannel(channel, MessageFormat.format("角色{0}创建成功",
                name));
    }

    private void initPlayer(Player player) {
        // 加载用户装备
        equipService.loadPlayerEquipBar(player);
        // 加载用户背包
        backBagService.loadPlayerBackBag(player);
        // 加载用户技能

        // 加载用户邮件
        emailService.loadEmail(player);
        // 加载用户好友
        friendService.loadFriends(player);
        // 初始化角色战斗属性
        playerPropertyService.initPlayerBattle(player);
        // 进入场景
        sceneService.initPlayerEntryScene(player);
    }

    public void logout(Player playerDomain) {
        // 退出场景
        sceneService.exitScene(playerDomain);
        // 保存数据
        playerDbService.updateAsync(playerDomain.getPlayerEntity());
        playerDomain.getChannel().attr(PLAYER_ENTITY_ATTRIBUTE_KEY).set(null);
        NotificationHelper.notifyPlayer(playerDomain,"退出当前角色");
        // 从缓存中剔除
        playerManager.removePlayerDomain(playerDomain.getPlayerEntity().getId());
    }

    public Player getPlayer(long playerId){
        return playerManager.getPlayerDomain(playerId);
    }
}
