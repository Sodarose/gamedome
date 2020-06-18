package com.game.module.chat;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.WordPage;
import com.game.module.player.OtherPlayerInfo;
import com.game.module.player.PlayerHandle;
import com.game.module.player.PlayerInfo;
import com.game.module.scene.SceneHandle;
import com.game.protocol.ChatProtocol;
import com.game.protocol.Message;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/17 9:59
 */
@Component
@ModuleHandler(module = ModuleKey.CHAT_MODULE)
public class ChatHandle extends BaseHandler {
    @Autowired
    private PlayerHandle playerHandle;
    @Autowired
    private WordPage wordPage;
    @Autowired
    private SceneHandle sceneHandle;
    @Autowired
    private ClientGameContext clientGameContext;

    /**
     * // 发送者Id
     * int64 senderId = 1;
     * // 发送者名称
     * string senderName = 2;
     * // 消息
     * string msg = 4;
     * // 接收者Id
     * int64  receiverId = 5;
     * int64  sendTime = 6;
     *
     * @param id
     * @param content
     * @return void
     */
    public void sendPrivacyChat(long id, String content) {
        PlayerInfo playerInfo = playerHandle.getPlayerInfo();
        if (playerInfo == null) {
            wordPage.print("请登录角色");
            return;
        }
        ChatProtocol.PrivacyMsg.Builder builder = ChatProtocol.PrivacyMsg.newBuilder();
        builder.setSenderId(playerInfo.getId());
        builder.setSenderName(playerInfo.getName());
        builder.setMsg(content);
        builder.setReceiverId(id);
        builder.setSendTime(System.currentTimeMillis());
        Message message = MessageUtil.createMessage(ModuleKey.CHAT_MODULE, ChatCmd.SEND_PRIVACY_MSG, builder.build()
                .toByteArray());
        clientGameContext.getChannel().writeAndFlush(message);
    }


    /**
     *     // 发送者Id
     *     int64  senderId = 1;
     *     // 发送者名称
     *     string senderName = 2;
     *     // 消息
     *     string msg = 4;
     *     // 频道Id
     *     int64  channelId = 5;
     *     // 频道类型
     *     int32  channelType = 6;
     *     int64  sendTime = 7;
     *
     * @param channelType
     * @param content
     * @return void
     */
    public void sendChannelChat(Integer channelType, String content) {
        PlayerInfo playerInfo = playerHandle.getPlayerInfo();
        if (playerInfo == null) {
            wordPage.print("请登录角色");
            return;
        }
        ChatProtocol.ChannelMsg.Builder builder = ChatProtocol.ChannelMsg.newBuilder();
        builder.setSenderId(playerInfo.getId());
        builder.setSenderName(playerInfo.getName());
        builder.setChannelType(channelType);
        builder.setMsg(content);
        builder.setSendTime(System.currentTimeMillis());
        Message message = MessageUtil.createMessage(ModuleKey.CHAT_MODULE, ChatCmd.SEND_CHANNEL_MSG, builder.build()
                .toByteArray());
        clientGameContext.getChannel().writeAndFlush(message);
    }

    /**
     *     int64  senderId = 1;
     *     string senderName = 2;
     *     string msg = 3;
     *     int64  sendTime = 6;
     *
     * @param content
     * @return void
     */
    public void sendLocalChat(String content) {
        PlayerInfo playerInfo = playerHandle.getPlayerInfo();
        if (playerInfo == null) {
            wordPage.print("请登录角色");
            return;
        }
        ChatProtocol.CommonMsg.Builder builder = ChatProtocol.CommonMsg.newBuilder();
        builder.setSenderId(playerInfo.getId());
        builder.setSenderName(playerInfo.getName());
        builder.setMsg(content);
        builder.setSendTime(System.currentTimeMillis());
        Message message = MessageUtil.createMessage(ModuleKey.CHAT_MODULE, ChatCmd.SEND_COMMON_MSG, builder.build()
                .toByteArray());
        clientGameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = ChatCmd.RECEIVE_PRIVACY_MSG)
    public void receivePrivacyChatMsg(Message message) {
        try {
            ChatProtocol.PrivacyMsg privacyMsg = ChatProtocol.PrivacyMsg.parseFrom(message.getData());
            wordPage.print("(私聊)"+privacyMsg.getSenderName()+":"+privacyMsg.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = ChatCmd.RECEIVE_COMMON_MSG)
    public void receiveLocalChatMsg(Message message) {
        try {
            ChatProtocol.CommonMsg commonMsg = ChatProtocol.CommonMsg.parseFrom(message.getData());
            wordPage.print(""+commonMsg.getSenderName()+":"+commonMsg.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = ChatCmd.RECEIVE_CHANNEL_MSG)
    public void receiveChannelChatMsg(Message message) {
        try {
            ChatProtocol.ChannelMsg channelMsg = ChatProtocol.ChannelMsg.parseFrom(message.getData());
            wordPage.print((channelMsg.getChannelType()==ChannelType.WORLD_CHAT?"(世界)":"(队伍)")
                    +channelMsg.getSenderName()+":"+channelMsg.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
