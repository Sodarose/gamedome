/*
package com.game.gameserver.module.achievement.entity;

import com.game.gameserver.common.config.AchievementConfig;
import com.game.gameserver.module.achievement.service.AchievementService;
import com.game.gameserver.module.achievement.type.AchievementState;
import com.game.gameserver.module.task.entity.TaskProgress;
import com.game.gameserver.util.GameUUID;
import com.game.gameserver.util.TaskUtil;
import lombok.Data;

import java.util.List;

*/
/**
 * 成就实体
 *
 * @author xuewenkang
 * @date 2020/7/1 20:53
 *//*

@Data
public class Achievement {
    */
/** 成就id *//*

    private Long id;
    */
/** 成就资源id *//*

    private Integer achievementId;
    */
/** 成就状态 *//*

    private int state;
    */
/** 成就所属 *//*

    private long playerId;
    */
/** 成就进度 *//*

    private List<TaskProgress> taskProgresses;

    public Achievement(){

    }


    */
/**
     * 新创建一个成就实体
     *
     * @param playerId
     * @param achievementConfig
     * @return com.game.gameserver.module.achievement.entity.Achievement
     *//*

    public static Achievement valueOf(long playerId, AchievementConfig achievementConfig){
        Achievement achievement = new Achievement();
        achievement.setId(GameUUID.getInstance().generate());
        achievement.setAchievementId(achievementConfig.getId());
        achievement.setPlayerId(playerId);
        achievement.setState(AchievementState.ACCEPTED);
        achievement.setTaskProgresses(TaskUtil.parserTaskRequire(achievementConfig
                .getTaskRequire()));
        return achievement;
    }
}
*/
