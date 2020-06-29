package com.game.gameserver.module.task.type;

/**
 * 任务状态
 *
 * @author xuewenkang
 * @date 2020/6/29 15:29
 */
public interface TaskState {
    /** 0 - 未接受 / 已放弃任务 */
    int UNACCEPT = 0;

    /** 1 - 已接任务(正在进行中) */
    int ACCEPTED = 1;

    /** 2 - 已完成任务(未领取奖励)*/
    int COMPLETED = 2;

    /** 3 - 已领取奖励(目前是客户端使用. 服务端不用)*/
    int REWARDS = 3;

    /** 4 - 任务已失败(目前该字段应用在押镖类型任务)*/
    int FAILED = 4;
}
