package com.game.gameserver.module.instance.service;

import com.game.gameserver.common.config.CheckPointConfig;
import com.game.gameserver.common.config.InstanceCheckPointConfig;
import com.game.gameserver.common.config.InstanceConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.instance.helper.InstanceHelper;
import com.game.gameserver.module.instance.manager.InstanceManager;
import com.game.gameserver.module.instance.model.CheckPoint;
import com.game.gameserver.module.instance.model.Instance;
import com.game.gameserver.module.instance.type.CheckPointState;
import com.game.gameserver.module.instance.type.InstanceEnum;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.monster.service.MonsterService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerDataService;
import com.game.gameserver.module.scene.SceneType;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.module.scene.service.SceneService;
import com.game.gameserver.module.team.model.Team;
import com.game.gameserver.module.team.service.TeamService;
import com.game.gameserver.util.GameUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/7/17 15:29
 */
@Service
public class InstanceService {

    @Autowired
    private InstanceManager instanceManager;
    @Autowired
    private TeamService teamService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private PlayerDataService playerDataService;


    private Instance createInstance(InstanceConfig instanceConfig) {
        if (instanceConfig == null) {
            return null;
        }
        // 获取关卡配置
        InstanceCheckPointConfig instanceCheckPointConfig = StaticConfigManager.getInstance()
                .getIntegerInstanceCheckpointConfigMap().get(instanceConfig.getId());
        if (instanceCheckPointConfig == null) {
            return null;
        }
        // 创建副本
        Instance instance = new Instance();
        instance.setInstanceConfig(instanceConfig);
        instance.setInstanceCheckpointConfig(instanceCheckPointConfig);
        instance.setId(GameUUID.getInstance().generate());
        instance.setCurrRound(0);
        instance.setCreatTime(System.currentTimeMillis());
        instance.setFailedTime(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(
                instanceConfig.getLimitTime(), TimeUnit.SECONDS
        ));
        instance.setState(InstanceEnum.RUNNING);
        instance.setReward(false);
        // 创建关卡
        CheckPoint checkPoint = createCheckPoint(instance,instanceCheckPointConfig.getCheckPointConfigList().get(0));
        if (checkPoint == null) {
            return null;
        }
        // 设置关卡
        instance.setCheckPoint(checkPoint);
        return instance;
    }

    /**
     * 创建初始关卡
     *
     * @param checkPointConfig
     * @return com.game.gameserver.module.instance.model.CheckPoint
     */
    private CheckPoint createCheckPoint(Instance instance,CheckPointConfig checkPointConfig) {
        if (checkPointConfig == null) {
            return null;
        }
        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setRound(checkPointConfig.getRound());
        checkPoint.setState(CheckPointState.RUNNING);
        checkPoint.setCondition(checkPointConfig.getCondition());
        checkPoint.setCheckPointConfig(checkPointConfig);
        // 加载怪物
        List<Integer> monsterIdList = checkPointConfig.getMonsters();
        monsterIdList.forEach(monsterId -> {
            Monster monster = monsterService.createMonster(monsterId);
            monster.setCurrScene(instance);
            checkPoint.getMonsterMap().put(monster.getId(), monster);
        });
        return checkPoint;
    }

    /**
     * 查看所有副本
     *
     * @param player
     * @return void
     */
    public void showAllInstance(Player player) {
        List<InstanceConfig> instanceConfigs = new ArrayList<>(StaticConfigManager.getInstance().getInstanceConfigMap().values());
        NotificationHelper.notifyPlayer(player, InstanceHelper.buildInstanceConfigList(instanceConfigs));
    }

    /**
     * 单人进入副本
     *
     * @param player
     * @param instanceId
     * @return void
     */
    public void entryInstance(Player player, int instanceId) {
        Scene currScene = player.getCurrScene();
        if (currScene.getSceneType() == SceneType.INSTANCE) {
            NotificationHelper.notifyPlayer(player, "你已经在副本中了");
            return;
        }
        // 验证副本
        InstanceConfig instanceConfig = StaticConfigManager.getInstance().getInstanceConfigMap().get(instanceId);
        if (instanceConfig == null) {
            NotificationHelper.notifyPlayer(player, "无此副本");
            return;
        }
        // 单人副本接口 组队无法进入
        if (player.getTeamId() != null) {
            NotificationHelper.notifyPlayer(player, "组队无法进入");
            return;
        }
        // 等级是否足够
        if (player.getLevel() < instanceConfig.getMinLevel()) {
            NotificationHelper.notifyPlayer(player, "当前等级不足 无法进入副本");
            return;
        }
        // 创建副本
        Instance instance = createInstance(instanceConfig);
        if (instance == null) {
            return;
        }
        // 玩家退出当前场景
        sceneService.exitScene(player);
        // 玩家进入副本
        instance.getCheckPoint().getPlayerMap().put(player.getId(), player);
        instanceManager.putInstance(instance.getId(), instance);
        // 设置当前场景为所在的副本
        player.setCurrScene(instance);
        // 同步副本信息
        NotificationHelper.notifyPlayer(player, "进入副本");
        NotificationHelper.syncInstance(instance);
    }

    /**
     * 组队进入副本
     *
     * @param player
     * @param instanceId
     * @return void
     */
    public void entryInstanceByTeam(Player player, int instanceId) {
        // 验证副本
        InstanceConfig instanceConfig = StaticConfigManager.getInstance().getInstanceConfigMap().get(instanceId);
        if (instanceConfig == null) {
            NotificationHelper.notifyPlayer(player, "无此副本");
            return;
        }
        // 组队副本接口 单人无法进入
        if (player.getTeamId() == null) {
            NotificationHelper.notifyPlayer(player, "组队无法进入");
            return;
        }
        Team team = teamService.getTeam(player.getTeamId());
        if (team == null) {
            return;
        }
        if (player.getId() != team.getCaptainId()) {
            NotificationHelper.notifyPlayer(player, "你不是队长");
            return;
        }
        // 检测队伍所有队员是否够资格进入副本
        for (Player member : team.getMemberMap().values()) {
            if (member.getLevel() < instanceConfig.getMinLevel()) {
                NotificationHelper.notifyTeam(team, "队伍中有人等级不足");
                return;
            }
        }
        // 创建副本
        Instance instance = createInstance(instanceConfig);
        // 队员退场景 并进入副本
        team.getMemberMap().values().forEach(member -> {
            // 退出原场景
            sceneService.exitScene(member);
            // 进入新副本
            instance.getPlayerMap().put(member.getId(), member);
            // 设置当前场景为副本
            member.setCurrScene(instance);
        });
        instanceManager.putInstance(instance.getId(), instance);
        // 同步数据
        NotificationHelper.notifyTeam(team, "进入副本");
        NotificationHelper.syncInstance(instance);
    }

    /**
     * 退出副本
     *
     * @param player
     * @return void
     */
    public void exitInstance(Player player) {
        if (player.getCurrScene().getSceneType() != SceneType.INSTANCE) {
            return;
        }
        Instance instance = (Instance) player.getCurrScene();
        // 如果是组队进入并且队伍中人数大于1的那么  会先退出组队 再退出副本
        if (player.getTeamId() != null) {
            Team team = teamService.getTeam(player.getTeamId());
            if (team != null && team.getMemberMap().size() > 1) {
                teamService.exitItem(player);
            }
        }
        // 退出副本
        instance.getPlayerMap().remove(player.getId());
        // 初始化状态
        playerDataService.initProperty(player);
        // 进入原场景
        sceneService.initPlayerEntryScene(player);
        NotificationHelper.notifyScene(instance,
                MessageFormat.format("{0}退出副本",
                        player.getName()));
    }
}
