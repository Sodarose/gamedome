package com.game.gameserver.net.modelhandler.task;

import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.task.service.TaskService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/29 15:23
 */
@Component
@ModuleHandler(module = ModuleKey.TASK_MODULE)
public class TaskHandle extends BaseHandler {
    @Autowired
    private TaskService taskService;

    @CmdHandler(cmd = TaskCmd.SHOW_ALL_TASK)
    public void showAllTask(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        taskService.showAllTask(player);
    }

    @CmdHandler(cmd = TaskCmd.SHOW_RECEIVE_ABLE_TASK)
    public void showReceiveAbleTask(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        taskService.showReceiveAbleTask(player);
    }

    @CmdHandler(cmd = TaskCmd.SHOW_RECEIVE_TASK)
    public void showReceiveTask(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        taskService.showReceiveTask(player);
    }

    @CmdHandler(cmd = TaskCmd.ACCEPT_TASK)
    public void acceptTask(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int taskId = Integer.parseInt(message.getContent());
        taskService.acceptTask(player,taskId);
    }

    @CmdHandler(cmd = TaskCmd.CANCEL_TASK)
    public void cancelTask(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int taskId = Integer.parseInt(message.getContent());
        taskService.cancelTask(player,taskId);
    }

    @CmdHandler(cmd = TaskCmd.SUBMIT_TASK)
    public void submitTask(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int taskId = Integer.parseInt(message.getContent());
        taskService.submitTask(player,taskId);
    }

}
