package com.game.gameserver.module.task.service;

import com.game.protocol.TaskProtocol;

/**
 * @author xuewenkang
 * @date 2020/6/29 10:17
 */
public interface TaskService {

    /**
     * 查询所有任务
     *
     * @param playerId
     * @return com.game.protocol.TaskProtocol.TaskListRes
     */
    TaskProtocol.QueryAllTaskRes queryAllTask(long playerId);

    /**
     * 获取可接受的任务列表
     *
     * @param playerId
     * @return com.game.protocol.TaskProtocol.QueryReceiveAbleTaskRes
     */
    TaskProtocol.QueryReceiveAbleTaskRes queryReceiveAbleTask(long playerId);

    /**
     * 查询用户已经接受的任务
     *
     * @param playerId
     * @return com.game.protocol.TaskProtocol.PlayerTaskListRes
     */
    TaskProtocol.QueryReceiveTaskRes queryReceiveTask(long playerId);

    /**
     * 接受任务
     *
     * @param playerId
     * @param taskId
     * @return com.game.protocol.TaskProtocol.AcceptTaskTes
     */
    TaskProtocol.AcceptTaskTes  acceptTask(long playerId,int taskId);

    /**
     * 取消任务
     *
     * @param playerId
     * @param taskId
     * @return com.game.protocol.TaskProtocol.CancelTaskRes
     */
    TaskProtocol.CancelTaskRes  cancelTask(long playerId,int taskId);

    /**
     * 提交任务
     *
     * @param playerId
     * @param taskId
     * @return com.game.protocol.TaskProtocol.SubmitTaskRes
     */
    TaskProtocol.SubmitTaskRes  submitTask(long playerId,int taskId);

    /**
     * 查看任务
     *
     * @param playerid
     * @param taskId
     * @return com.game.protocol.TaskProtocol.CheckTaskRes
     */
    TaskProtocol.CheckTaskRes   checkTask(long playerid,int taskId);
}
