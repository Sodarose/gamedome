package com.game.gameserver.module.task.entity;

import lombok.Data;

/**
 * 任务进度
 *
 * @author xuewenkang
 * @date 2020/6/29 17:48
 */
@Data
public class TaskProgress {
    /**
     * 行为事件类型
     */
    private int type;

    /**
     * 目标
     */
    private int target;

    /**
     * 需要完成数量
     */
    private int amount;

    /**
     * 已完成数量
     */
    private int num;

    /**
     * 当前条件状态 true当前进度已经完成
     */
    private boolean complete;

    public TaskProgress(int type, int target, int amount) {
        this.type = type;
        this.target = target;
        this.amount = amount;
        this.num = 0;
        this.complete = false;
    }
}
