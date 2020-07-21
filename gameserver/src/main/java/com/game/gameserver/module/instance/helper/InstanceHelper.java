package com.game.gameserver.module.instance.helper;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.instance.model.CheckPoint;
import com.game.gameserver.module.instance.model.Instance;
import com.game.gameserver.module.monster.helper.MonsterHelper;
import com.game.gameserver.module.task.model.Award;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/7/17 16:52
 */
public class InstanceHelper {

    public static String buildInstanceConfigList(List<InstanceConfig> instanceConfigs) {
        StringBuilder sb = new StringBuilder("副本信息列表").append("\n");
        instanceConfigs.forEach(instanceConfig -> {
            sb.append(buildInstanceConfigMsg(instanceConfig)).append("\n");
            sb.append("\n");
        });
        return sb.toString();
    }

    public static String buildInstanceConfigMsg(InstanceConfig instanceConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("副本Id:").append(instanceConfig.getId()).append("\n");
        sb.append("副本名称:").append(instanceConfig.getName()).append("\n");
        sb.append("时间限制:").append(instanceConfig.getLimitTime()).append("\n");
        sb.append("等级限制:").append(instanceConfig.getMinLevel()).append("\n");
        sb.append("是否组队:").append(instanceConfig.isNeedTeam() ? "是" : "否").append("\n");
        if (instanceConfig.isNeedTeam()) {
            sb.append("队伍人数限制:").append(instanceConfig.getMaxNum()).append("~")
                    .append(instanceConfig.getMaxNum()).append("\n");
        }
        sb.append("经验奖励:").append(instanceConfig.getExprAward()).append("\n");
        sb.append("金币奖励:").append(instanceConfig.getGoldAward()).append("\n");
        sb.append("道具奖励:").append("\n");
        for (Award award : instanceConfig.getItemAward()) {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(award.getItemId());
            sb.append(itemConfig.getName()).append(" x ").append(award.getNum()).append("\n");
        }
        sb.append("副本介绍:").append(instanceConfig.getDesc()).append("\n");
        return sb.toString();
    }

    public static String buildInstanceSceneMsg(Instance instance) {
        StringBuilder sb = new StringBuilder("副本信息").append("\t\t\t\t\t\t\t\t").append("剩余时间:").append(
                TimeUnit.SECONDS.convert(instance.getFailedTime()-System.currentTimeMillis(),
                        TimeUnit.MILLISECONDS)
        ).append("\n");
        sb.append("副本名称:").append(instance.getName()).append("\n");
        sb.append("副本介绍:").append(instance.getInstanceConfig().getDesc()).append("\n");
        sb.append("当前关卡信息:").append("\n");
        CheckPoint checkPoint = instance.getCheckPoint();
        sb.append("当前关卡").append(checkPoint.getCheckPointConfig().getRound()).append("\n");
        sb.append(checkPoint.getCheckPointConfig().getDesc()).append("\n");
        sb.append("玩家列表:").append("\n");
        checkPoint.getPlayerMap().values().forEach(player -> {
            sb.append(player.getPlayerEntity().getName()).append("(")
                    .append(player.getPlayerBattle().getHp()).append(")")
                    .append("\t");
        });
        sb.append("\n");
        sb.append("怪物列表:");
        checkPoint.getMonsterMap().values().forEach(monster -> {
            sb.append(monster.getName()).append("(").append(MonsterHelper.buildStateMsg(monster)).append(")").append("(").append(monster.getCurrHp()).append(")")
                    .append("\t");
        });
        sb.append("\n");
        sb.append("召唤物列表:");
        checkPoint.getPetMap().values().forEach(pet -> {
            sb.append(pet.getName()).append("(").append(pet.getCurrHp()).append(")")
                    .append("\t");
        });
        return sb.toString();
    }
}
