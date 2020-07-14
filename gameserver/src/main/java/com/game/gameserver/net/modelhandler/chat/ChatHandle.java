package com.game.gameserver.net.modelhandler.chat;

import com.game.gameserver.module.chat.service.ChatService;
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
 * @author kangkang
 */
@Component
@ModuleHandler(module = ModuleKey.CHAT_MODULE)
public class ChatHandle extends BaseHandler {

    @Autowired
    private ChatService chatService;


    /**
     * 私聊
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ChatCmd.PRIVATE_CHAT)
    public void privateChat(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        long targetId = Long.parseLong(param[0]);
        String content = param[1];
        chatService.privateChat(player,targetId,content);
    }

    /**
     * 本地聊天
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ChatCmd.LOCAL_CHAT)
    public void localChat(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String content = message.getContent();
        chatService.localChat(player,content);
    }

    /**
     * 频道聊天
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ChatCmd.CHANNEL_CHAT)
    public void channelChat(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        int channelType = Integer.parseInt(param[0]);
        String content = param[1];
        chatService.channelChat(player,channelType,content);
    }
}
