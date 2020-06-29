package com.game.gameserver.net.modelhandler.task;

/**
 * @author xuewenkang
 * @date 2020/6/29 15:00
 */
public interface TaskCmd {
    /** 接取任务 */
    short ACCEPT_TASK = 1;
    /** 提交任务 */
    short SUBMIT_TASK = 2;
    /** 取消任务*/
    short CANCEL_TASK = 3;
    /**  查看任务列表*/
    short TASK_LIST = 4;
    /**  查看已经接取的列表*/
    short TASK_LIST_BY_ACCEPT = 5;
}
