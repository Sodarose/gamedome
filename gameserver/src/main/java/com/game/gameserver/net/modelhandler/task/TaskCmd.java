package com.game.gameserver.net.modelhandler.task;

/**
 * @author xuewenkang
 * @date 2020/6/29 15:00
 */
public interface TaskCmd {
    /** 查询所有任务 */
    short QUERY_ALL_TASK = 1;
    /** 查询可接受任务 */
    short QUERY_RECEIVE_ABLE_TASK = 2;
    /** 查询已经接受的任务*/
    short QUERY_RECEIVE_TASK = 3;
    /** 接取任务 */
    short ACCEPT_TASK = 4;
    /** 提交任务 */
    short SUBMIT_TASK = 5;
    /** 取消任务*/
    short CANCEL_TASK = 6;
    /** 查看任务 */
    short CHECK_TASK = 6;
}
