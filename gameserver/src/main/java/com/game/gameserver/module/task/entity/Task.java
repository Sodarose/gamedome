package com.game.gameserver.module.task.entity;

import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.module.task.type.TaskState;
import com.game.gameserver.util.GameUUID;
import lombok.Data;

import java.util.List;

/**
 * 角色任务实体  需存档
 *
 * @author xuewenkang
 * @date 2020/6/29 16:21
 */
@Data
public class Task {
    /** 存储在数据库中的id*/
    private long id;

    /** 任务资源id*/
    private int taskId;

    /** 任务状态*/
    private int state;

    /** 任务所属的角色*/
    private long playerId;

    /** 任务进度/要求 */
    private List<TaskProgress> taskProgresses;

    public Task(long playerId,TaskConfig taskConfig){
        this.id = GameUUID.getInstance().generate();
        this.taskId = taskConfig.getId();
        // 设定为已接受状态（进行中）
        this.state = TaskState.ACCEPTED;
        this.playerId = playerId;
        // 解析要求
        this.taskProgresses = taskConfig.parseTaskRequire();
    }

}
