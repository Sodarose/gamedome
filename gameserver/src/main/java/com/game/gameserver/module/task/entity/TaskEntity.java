package com.game.gameserver.module.task.entity;

import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.module.task.model.TaskProgress;
import com.game.gameserver.module.task.type.TaskState;
import com.game.gameserver.util.GameUUID;
import com.game.gameserver.util.TaskUtil;
import lombok.Data;

import java.util.List;

/**
 * 角色任务实体  需存档
 *
 * @author xuewenkang
 * @date 2020/6/29 16:21
 */
@Data
public class TaskEntity {
    /** 任务资源id*/
    private Integer taskId;

    /** 任务状态*/
    private Integer state;

    /** 任务所属的角色*/
    private Long playerId;

    /** 任务进度 */
    private String taskProgresses;

    public TaskEntity(){

    }
}
