package com.game.gameserver.module.achievement.entity;

import com.game.gameserver.common.config.AchievementConfig;
import com.game.gameserver.module.achievement.service.AchievementService;
import com.game.gameserver.module.achievement.type.AchievementState;
import com.game.gameserver.module.task.model.TaskProgress;
import com.game.gameserver.util.GameUUID;
import com.game.gameserver.util.TaskUtil;
import lombok.Data;

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
    private int state;

    /** 角色Id */
    private long playerId;

    /** 成就进度表  目标，成就进度*/
    private Map<Integer,TaskProgress> taskProgressMap;

}
