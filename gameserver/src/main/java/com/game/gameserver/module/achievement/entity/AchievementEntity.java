package com.game.gameserver.module.achievement.entity;

import com.game.gameserver.module.task.model.TaskProgress;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author kangkang
 */
@Data
public class AchievementEntity {
    /** 成就Id */
    private Integer achievementId;
    /** 角色状态 */
    private Integer state;
    /** 角色Id */
    private Long playerId;
    /** 成就进  key:目标  value:成就进度*/
    private Map<Integer,TaskProgress> taskProgress = new HashMap<>();

}
