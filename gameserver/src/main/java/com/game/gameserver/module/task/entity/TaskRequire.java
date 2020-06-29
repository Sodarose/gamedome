package com.game.gameserver.module.task.entity;

import lombok.Data;

/**
 * 任务要求
 *
 * @author xuewenkang
 * @date 2020/6/29 17:48
 */
@Data
public class TaskRequire {
    /** 行为动作 */
    private int type;

    /** 任务目标 */
    private int target;

    /** 需要完成数量*/
    private int amount;

    /** 已完成数量*/
    private int num;

    /** 当前条件状态 true当前状态已经完成 */
    private boolean complete;
}
