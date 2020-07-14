package com.game.gameserver.module.task.model;

import com.game.gameserver.module.task.entity.TaskEntity;
import lombok.Data;

/**
 * 任务模型
 *
 * @author xuewenkang
 * @date 2020/7/14 18:00
 */
@Data
public class Task {
    /** 存储在数据库中的id*/
    private Long id;

    /** 任务资源id*/
    private Integer taskId;

    /** 任务状态*/
    private Integer state;

    /** 任务所属的角色*/
    private Long playerId;
}
