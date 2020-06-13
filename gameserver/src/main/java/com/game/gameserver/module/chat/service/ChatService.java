package com.game.gameserver.module.chat.service;

import com.game.gameserver.common.Result;

/**
 * @author xuewenkang
 * @date 2020/6/10 20:08
 */
public interface ChatService {
    /**
     * 私聊
     *
     * @param playerId
     * @param channelId
     * @param msg
     * @return com.game.gameserver.common.Result
     */
    Result talkChatChannel(int playerId,int channelId,String msg);

    /**
     * 世界聊天
     *
     * @param playerId
     * @param msg
     * @return com.game.gameserver.common.Result
     */
    Result talkWorldChannel(int playerId,String msg);
}
