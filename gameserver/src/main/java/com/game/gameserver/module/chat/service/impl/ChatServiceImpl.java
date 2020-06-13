package com.game.gameserver.module.chat.service.impl;

import com.game.gameserver.common.Result;
import com.game.gameserver.module.chat.service.ChatService;
import org.springframework.stereotype.Service;

/**
 * 聊天服务
 *
 * @author xuewenkang
 * @date 2020/6/10 20:08
 */
@Service
public class ChatServiceImpl implements ChatService {
    /**
     * 私聊
     *
     * @param playerId
     * @param channelId
     * @param msg
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Result talkChatChannel(int playerId, int channelId, String msg) {
        
        return null;
    }

    /**
     * 世界聊天
     *
     * @param playerId
     * @param msg
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Result talkWorldChannel(int playerId, String msg) {
        return null;
    }
}
