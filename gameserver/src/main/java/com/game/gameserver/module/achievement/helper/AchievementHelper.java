
package com.game.gameserver.module.achievement.helper;


import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.achievement.model.Achievement;
import com.game.gameserver.module.achievement.model.PlayerAchievement;
import com.game.gameserver.module.task.model.TaskCondition;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/1 21:18
 */

public class AchievementHelper {
    public static String buildAchievementMsg(PlayerAchievement playerAchievement){
        StringBuilder sb = new StringBuilder("任务列表:").append("\n");
        playerAchievement.getAchievementMap().values().forEach(achievement -> {
            sb.append(buildAchievementMsg(achievement)).append("\n").append("\n");
        });
        return sb.toString();
    }

    public static String buildAchievementMsg(Achievement achievement){
        StringBuilder sb = new StringBuilder();
        sb.append("成就Id:").append(achievement.getAchievementConfig().getId()).append("\n");
        sb.append("成就名称:").append(achievement.getAchievementConfig().getName()).append("\n");
        sb.append("成就介绍:").append(achievement.getAchievementConfig().getDescription()).append("\n");
        sb.append("成就状态:").append(achievement.getState()).append("\n");
        sb.append("成就进度:").append("\n");
        achievement.getTaskProgress().values().forEach(taskProgress -> {
            TaskCondition taskCondition = achievement.getAchievementConfig().getTaskConditionMap().get(taskProgress.getTarget());
            sb.append(taskCondition.getDesc())
                    .append(":\t").append(taskProgress.getProgress())
                    .append("/").append(taskCondition.getAmount()).append("\n");
        });
        sb.append("\n");
        sb.append("成就奖励:").append("\n");
        sb.append("经验奖励:").append(achievement.getAchievementConfig().getExprAward()).append("\n");
        sb.append("金币奖励:").append(achievement.getAchievementConfig().getGoldAward()).append("\n");
        sb.append("道具奖励:").append("\n");
        achievement.getAchievementConfig().getItemAward().forEach(award -> {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(award.getItemId());
            sb.append(itemConfig.getName()).append(" x ").append(award.getNum()).append("\n");
        });
        return sb.toString();
    }
}

