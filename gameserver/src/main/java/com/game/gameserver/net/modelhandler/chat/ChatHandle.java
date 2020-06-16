package com.game.gameserver.net.modelhandler.chat;

import com.game.gameserver.module.chat.service.ChatService;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.ChatProtocol;
import com.game.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/15 17:44
 */
@Component
@ModuleHandler(module = ModuleKey.CHAT_MODULE)
public class ChatHandle extends BaseHandler {


    @Autowired
    private ChatService chatService;

    /**
     * 发送频道消息
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ChatCmd.SEND_CHANNEL_MSG)
    public void sendChannelMsgHandle(Message message, Channel channel) {
        PlayerObject playerObject = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerObject ==null){
            return;
        }
        try {
            ChatProtocol.ChannelMsg channelMsg = ChatProtocol
                    .ChannelMsg.parseFrom(message.getData());
            chatService.sendChannelMsg(playerObject,channelMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送私聊消息
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ChatCmd.SEND_PRIVACY_MSG)
    public void sendPrivacyMsgHandle(Message message, Channel channel) {
        PlayerObject playerObject = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerObject ==null){
            return;
        }
        try {
            ChatProtocol.PrivacyMsg privacyMsg = ChatProtocol.PrivacyMsg
                    .parseFrom(message.getData());
            chatService.sendPrivacyMsg(playerObject,privacyMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送普通消息
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ChatCmd.SEND_COMMON_MSG)
    public void sendCommonMsgHandle(Message message, Channel channel) {
        PlayerObject playerObject = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerObject ==null){
            return;
        }
        try {
            ChatProtocol.CommonMsg commonMsg = ChatProtocol
                    .CommonMsg.parseFrom(message.getData());
            chatService.sendCommonMsg(playerObject,commonMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
