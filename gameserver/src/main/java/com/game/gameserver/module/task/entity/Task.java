package com.game.gameserver.module.task.entity;

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

    /** 进度 */
    private int progress;

    /** 任务所属的角色*/
    private long playerId;

    /** 任务要求 */
    private List<TaskRequire> taskRequireList;
}
