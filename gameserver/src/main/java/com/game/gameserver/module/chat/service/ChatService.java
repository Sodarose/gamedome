package com.game.gameserver.module.chat.service;

import com.game.gameserver.module.player.model.PlayerObject;
import com.game.protocol.ChatProtocol;

/**
 * @author xuewenkang
 * @date 2020/6/10 20:08
 */
public interface ChatService {

    /**
     * 频道消息
     *
     * @param playerObject
     * @param channelMsg
     * @return void
     */
    void sendChannelMsg(PlayerObject playerObject, ChatProtocol.ChannelMsg channelMsg);

    /**
     * 私聊消息
     *
     * @param playerObject
     * @param privacyMsg
     * @return void
     */
    void sendPrivacyMsg(PlayerObject playerObject, ChatProtocol.PrivacyMsg privacyMsg);

    /**
     * 普通消息
     *
     * @param playerObject
     * @param commonMsg
     * @return void
     */
    void sendCommonMsg(PlayerObject playerObject, ChatProtocol.CommonMsg commonMsg);
}
