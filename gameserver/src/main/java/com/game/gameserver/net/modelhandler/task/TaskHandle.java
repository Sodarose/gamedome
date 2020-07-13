package com.game.gameserver.net.modelhandler.task;

import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/29 15:23
 */
@Component
@ModuleHandler(module = ModuleKey.TASK_MODULE)
public class TaskHandle extends BaseHandler {

   /* @Autowired
    private TaskService taskService;

    *//**
     * 查询所有任务
     *
     * @param message
     * @param channel
     * @return void
     *//*
    @CmdHandler(cmd = TaskCmd.QUERY_ALL_TASK)
    public void handleQueryAllTaskReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        TaskProtocol.QueryAllTaskRes res = taskService.queryAllTask(player.getId());
        Message resMsg = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.QUERY_ALL_TASK,
                res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    *//**
     * 查询可接受的任务
     *
     * @param message
     * @param channel
     * @return void
     *//*
    @CmdHandler(cmd = TaskCmd.QUERY_RECEIVE_ABLE_TASK)
    public void handleQueryReceiveAbleTask(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        TaskProtocol.QueryReceiveAbleTaskRes res = taskService.queryReceiveAbleTask(player.getId());
        Message resMsg = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.QUERY_RECEIVE_ABLE_TASK,
                res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    *//**
     * 查询已经接受的任务
     *
     * @param message
     * @param channel
     * @return void
     *//*
    @CmdHandler(cmd = TaskCmd.QUERY_RECEIVE_TASK)
    public void handleQueryReceiveTaskReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        TaskProtocol.QueryReceiveTaskRes res = taskService.queryReceiveTask(player.getId());
        Message resMsg = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.QUERY_RECEIVE_TASK, res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    *//**
     * 接受任务请求
     *
     * @param message
     * @param channel
     * @return void
     *//*
    @CmdHandler(cmd = TaskCmd.ACCEPT_TASK)
    public void handleAcceptTaskReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            TaskProtocol.AcceptTaskReq req = TaskProtocol.AcceptTaskReq.parseFrom(message.getData());
            TaskProtocol.AcceptTaskTes res = taskService.acceptTask(player.getId(), req.getTaskId());
            Message resMsg = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.ACCEPT_TASK, res
                    .toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    *//**
     * 取消任务
     *
     * @param message
     * @param channel
     * @return void
     *//*
    @CmdHandler(cmd = TaskCmd.CANCEL_TASK)
    public void handleCancelTaskReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            TaskProtocol.CancelTaskReq req = TaskProtocol.CancelTaskReq.parseFrom(message.getData());
            TaskProtocol.CancelTaskRes res = taskService.cancelTask(player.getId(),req.getTaskId());
            Message resMsg = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.CANCEL_TASK, res
                    .toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    *//**
     * 提交任务
     *
     * @param message
     * @param channel
     * @return void
     *//*
    @CmdHandler(cmd = TaskCmd.SUBMIT_TASK)
    public void handleSubmitTaskReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            TaskProtocol.SubmitTaskReq req = TaskProtocol.SubmitTaskReq.parseFrom(message.getData());
            TaskProtocol.SubmitTaskRes res = taskService.submitTask(player.getId(),req.getTaskId());
            Message resMsg = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.SUBMIT_TASK, res
                    .toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    *//**
     * 查看任务
     *
     * @param message
     * @param channel
     * @return void
     *//*
    @CmdHandler(cmd = TaskCmd.CHECK_TASK)
    public void handleCheckTaskReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            TaskProtocol.CheckTaskReq req = TaskProtocol.CheckTaskReq.parseFrom(message.getData());
            TaskProtocol.CheckTaskRes res = taskService.checkTask(player.getId(),req.getTaskId());
            Message resMsg = MessageUtil.createMessage(ModuleKey.TASK_MODULE, TaskCmd.CHECK_TASK, res
                    .toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
*/

}
