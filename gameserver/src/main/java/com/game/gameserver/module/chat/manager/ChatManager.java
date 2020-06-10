package com.game.gameserver.module.chat.manager;

import com.game.gameserver.module.chat.model.ChatChannel;
import com.game.gameserver.module.chat.model.WorldChannel;
import com.game.gameserver.module.player.model.PlayerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天管理类
 *
 * @author xuewenkang
 * @date 2020/6/10 19:43
 */
@Component
public class ChatManager {
    private final static Logger logger = LoggerFactory.getLogger(ChatManager.class);

    /** 全服聊天频道 */
    private final WorldChannel worldChannel = new WorldChannel();
    /** 私聊频道 */
    private final Map<Integer, ChatChannel> channelMap = new ConcurrentHashMap<>(16);

    /**
     * 建立私聊通道
     *
     * @param playerObject 创建者
     * @param targetId 对方Id
     * @return void
     */
    public void createChatChannel(PlayerObject playerObject,int targetId){

    }
}
