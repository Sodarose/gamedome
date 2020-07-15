package com.game.gameserver.module.task.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 任务进度
 *
 * @author xuewenkang
 * @date 2020/6/29 17:48
 */
@Data
public class TaskProgress implements Serializable {

    /**
     * 任务条件Id
     */
    private int target;
    /**
     * 当前进度
     */
    private int progress;
    /**
     * 当前条件状态 true当前进度已经完成
     */
    private boolean completed = false;

    public void addProgress(int value) {
        this.progress += value;
    }

    public TaskProgress(int target){
        this.target = target;
        this.progress = 0;
        this.completed = false;
    }
}
