package com.game.gameserver.module.task.type;

/**
 * 任务状态
 *
 * @author xuewenkang
 * @date 2020/6/29 15:29
 */
public interface TaskState {

    /** 1 - 已接任务(正在进行中) */
    int ACCEPTED = 1;

    /** 2 - 已完成任务(未领取奖励)*/
    int COMPLETED = 2;

    /** 3 - 已完成任务(已经领取奖励)*/
    int REWARDS = 3;

}
