package com.game.gameserver.net.modelhandler.instance;

import com.game.gameserver.module.instance.service.InstanceService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
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
 * @date 2020/7/18 17:11
 */
@Component
@ModuleHandler(module = ModuleKey.INSTANCE_MODULE)
public class InstanceHandle extends BaseHandler {

    @Autowired
    private InstanceService instanceService;

    @CmdHandler(cmd = InstanceCmd.SHOW_ALL_INSTANCE)
    public void showAllInstance(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        instanceService.showAllInstance(player);
    }

    @CmdHandler(cmd = InstanceCmd.ENTRY_INSTANCE)
    public void entryInstance(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int instanceId = Integer.parseInt(message.getContent());
        instanceService.entryInstance(player,instanceId);
    }

    @CmdHandler(cmd = InstanceCmd.ENTRY_INSTANCE_BY_TEAM)
    public void entryInstanceByTeam(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int instanceId = Integer.parseInt(message.getContent());
        instanceService.entryInstanceByTeam(player,instanceId);
    }

    @CmdHandler(cmd = InstanceCmd.EXIT_INSTANCE)
    public void exitInstance(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        instanceService.exitInstance(player);
    }
}
