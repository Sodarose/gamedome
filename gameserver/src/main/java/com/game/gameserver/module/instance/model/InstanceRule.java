package com.game.gameserver.module.instance.model;

import com.game.gameserver.common.config.InstanceConfig;
import com.game.gameserver.common.config.InstanceMonster;
import com.game.gameserver.common.config.InstanceMonsterConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.instance.manager.InstanceManager;
import com.game.gameserver.module.instance.type.InstanceEnum;
import com.game.gameserver.module.item.manager.ItemManager;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.monster.type.MonsterType;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.instance.InstanceCmd;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.InstanceProtocol;
import com.game.protocol.Message;
import com.game.protocol.TipProtocol;
import com.game.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 副本玩法
 *
 * @author xuewenkang
 * @date 2020/6/20 12:21
 */
public class InstanceRule {
    private final static Logger logger = LoggerFactory.getLogger(InstanceRule.class);

    private final static InstanceRule INSTANCE_RULE = new InstanceRule();

    public static InstanceRule getInstanceRule(){
        return INSTANCE_RULE;
    }

    private InstanceRule() {

    }

    public void action(InstanceObject instanceObject) {
        if (instanceObject == null) {
            return;
        }

        syncInstanceInfo(instanceObject);

        // 是否需要回收
        if (instanceObject.isRecovery()) {
            logger.info("回收副本");
            Long currTime = System.currentTimeMillis();
            // 小于回收时间 不会收回
            if (currTime <= instanceObject.getRecoveryTime()) {
                return;
            }
            doRecovery(instanceObject);
            return;
        }
        // 副本内人员为空
        if (instanceObject.getCurrPlayers().isEmpty()) {
            logger.info("副本内人员为空");
            // 直接回收
            doRecovery(instanceObject);
            return;
        }
        // 挑战失败
        if (instanceObject.getState().equals(InstanceEnum.FAILED)) {
            logger.info("挑战副本失败");
            doFailed(instanceObject);
            return;
        }
        // 挑战成功
        if (instanceObject.getState().equals(InstanceEnum.SUCCESS)) {
            logger.info("挑战副本成功");
            doSuccess(instanceObject);
            return;
        }
        // 超时 挑战失败
        Long currTime = System.currentTimeMillis();
        Long endTime = instanceObject.getEndTime();
        if (currTime >= endTime) {
            instanceObject.setState(InstanceEnum.FAILED);
            return;
        }
        // 判断是否已经闯关成功
        // 副本配置
        InstanceConfig instanceConfig = StaticConfigManager
                .getInstance().getInstanceConfigMap().get(instanceObject.getInstanceConfigId());
        if (instanceConfig == null) {
            return;
        }
        InstanceMonsterConfig instanceMonsterConfig = StaticConfigManager.getInstance()
                .getInstanceMonsterConfigMap()
                .get(instanceConfig.getInstanceMonsterConfigId());
        if (instanceMonsterConfig == null) {
            logger.warn("副本没有静态配置数据 副本Id:{}", instanceObject.getId());
            return;
        }
        // 当前关卡
        int currRound = instanceObject.getCurrRound();
        int roundCount = instanceMonsterConfig.getRoundCount();
        // 判断是否通关
        if (currRound == roundCount) {
            // 最后一个关卡怪物被消灭 如果被消灭则通关
            if (instanceObject.isEmptyMonster()) {
                instanceObject.setState(InstanceEnum.SUCCESS);

                // 提示进入闯关成功
            }
        } else {
            // 判断当前关卡是否到达通关条件 怪物死亡表示可以进入下一个关卡
            if (instanceObject.isEmptyMonster()) {
                // 关卡数
                currRound += 1;
                // 生成当前关卡怪物配置列表
                List<InstanceMonster> monsterConfigList = instanceMonsterConfig.getInstanceMonsterList();
                InstanceMonster monsterConfig = null;
                // 取下一个回合的配置
                for (InstanceMonster instanceMonster : monsterConfigList) {
                    if (instanceMonster.getRoundIndex() == currRound) {
                        monsterConfig = instanceMonster;
                        break;
                    }
                }
                if (monsterConfig == null) {
                    return;
                }
                // 生成当前关卡怪物
                List<Long> monsterList = MonsterManager.instance.createMonsterObjectList(
                        monsterConfig.getMonsterId(),
                        monsterConfig.getCount(),
                        MonsterType.INSTANCE_MONSTER,
                        instanceObject.getId());
                // 放入副本
                instanceObject.addMonsters(monsterList);
                // 进入下一个关卡
                instanceObject.setCurrRound(currRound);
                // 发给前端 提示准备进入下一回合
                // 提示闯关失败
                TipProtocol.TipMessage.Builder builder = TipProtocol.TipMessage.newBuilder();
                List<Long> players = instanceObject.getCurrPlayers();
                builder.setMsg("进入下一个关卡");
                Message message = MessageUtil.createMessage(ModuleKey.TIP_MODULE, (short) 0, builder.build().toByteArray());
                for (Long playerId : players) {
                    PlayerObject playerObject = PlayerManager.instance.getPlayerObject(playerId);
                    if (playerObject == null) {
                        continue;
                    }
                    playerObject.getChannel().writeAndFlush(message);
                }
            }
        }
    }


    /**
     * 失败
     *
     * @param instanceObject
     * @return void
     */
    private void doFailed(InstanceObject instanceObject) {
        // 提示闯关失败
        TipProtocol.TipMessage.Builder builder = TipProtocol.TipMessage.newBuilder();
        List<Long> players = instanceObject.getCurrPlayers();
        builder.setMsg("闯关失败 10秒后退出");
        Message message = MessageUtil.createMessage(ModuleKey.TIP_MODULE, (short) 0, builder.build().toByteArray());
        for (Long playerId : players) {
            PlayerObject playerObject = PlayerManager.instance.getPlayerObject(playerId);
            if (playerObject == null) {
                continue;
            }
            playerObject.getChannel().writeAndFlush(message);
        }
        // 设定回收期限
        Long recoveryTime = System.currentTimeMillis()+InstanceObject.DURATION;
        instanceObject.setRecoveryTime(recoveryTime);
        // 设定回收
        instanceObject.setRecovery(true);
    }

    /**
     * 成功
     *
     * @param instanceObject
     * @return void
     */
    public void doSuccess(InstanceObject instanceObject) {
        // 提示成功
        List<Long> players = instanceObject.getCurrPlayers();
        InstanceConfig instanceConfig = StaticConfigManager.getInstance().getInstanceConfigMap().get(instanceObject
                .getInstanceConfigId());
        if(instanceConfig==null){
            return;
        }
        InstanceProtocol.InstanceSuccess instanceSuccess = ProtocolFactory.createInstanceSuccess(instanceConfig);
        Message message = MessageUtil.createMessage(ModuleKey.TIP_MODULE, (short) 0, instanceSuccess.toByteArray());
        for (Long playerId : players) {
            PlayerObject playerObject = PlayerManager.instance.getPlayerObject(playerId);
            if (playerObject == null) {
                continue;
            }
            // 发放副本通关奖励
            // 经验奖励
            int expr = instanceConfig.getExprAward();
            // 金币奖励
            int golds = instanceConfig.getGoldAward();
            playerObject.addExpr(expr);
            playerObject.addGolds(golds);
            // 道具奖励
            ItemManager.instance.addInstanceAward(playerId,instanceConfig);
            playerObject.getChannel().writeAndFlush(message);
        }
        // 设定回收期限
        Long recoveryTime = System.currentTimeMillis()+InstanceObject.DURATION;
        instanceObject.setRecoveryTime(recoveryTime);
        // 设定回收
        instanceObject.setRecovery(true);
    }

    /**
     * 副本完成
     *
     * @param instanceObject
     * @return void
     */
    private void doRecovery(InstanceObject instanceObject) {
        // 踢出还在副本内的角色
        List<Long> players = instanceObject.getCurrPlayers();
        SceneManager.instance.receiveInstancePlayers(players);
        instanceObject.getCurrPlayers().clear();
        // 回收副本
        InstanceManager.instance.removeInstance(instanceObject);
    }

    /**
     * 同步副本数据 测试用
     *
     * @param instanceObject
     * @return void
     */
    private void syncInstanceInfo(InstanceObject instanceObject){
        InstanceProtocol.InstanceInfo info = ProtocolFactory.createInstanceInfo(instanceObject);
        assert info != null;
        Message message = MessageUtil.createMessage(ModuleKey.INSTANCE_MODULE, InstanceCmd.SYNC_INSTANCE_INFO,
                info.toByteArray());
        List<Long> players = instanceObject.getCurrPlayers();
        for (Long playerId : players) {
            PlayerObject playerObject = PlayerManager.instance.getPlayerObject(playerId);
            if (playerObject == null) {
                continue;
            }
            playerObject.getChannel().writeAndFlush(message);
        }
    }
}
