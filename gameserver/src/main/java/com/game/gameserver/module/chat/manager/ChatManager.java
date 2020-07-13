package com.game.gameserver.module.chat.manager;

import com.game.gameserver.event.Listener;
import com.game.gameserver.module.chat.entity.ChatChannel;
import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.util.GameUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/15 16:26
 */
@Component
@Listener
public class ChatManager {
    private final static Logger logger = LoggerFactory.getLogger(ChatManager.class);
    private final Map<Long, ChatChannel> channelMap = new ConcurrentHashMap<>(16);

    public static ChatManager instance;

    public ChatManager() {
        instance = this;
    }

    /**
     * 初始化部分频道
     */
    public void initialize() {
        logger.info("初始化部分聊天频道");
        // 创建世界频道
        ChatChannel worldChatChannel = new ChatChannel(0L);
        channelMap.put(worldChatChannel.getChanelId(), worldChatChannel);
    }

    public Long createChatChannel() {
        ChatChannel chatChannel = new ChatChannel(GameUUID.getInstance().generate());
        channelMap.put(chatChannel.getChanelId(), chatChannel);
        return chatChannel.getChanelId();
    }

    /**
     * 进入频道
     *
     * @param player
     * @param channelId
     * @return void
     */
    public void entryChannel(PlayerEntity player, Long channelId) {
        ChatChannel chatChannel = channelMap.get(channelId);
        if (chatChannel == null) {
            return;
        }
        chatChannel.entry(player.getId());
    }

    /**
     * 退出频道
     *
     * @param player
     * @param channelId
     * @return void
     */
    public void exitChannel(PlayerEntity player, Long channelId) {
        ChatChannel chatChannel = channelMap.get(channelId);
        if (channelId == null) {
            return;
        }
        chatChannel.exit(player.getId());
    }



    public void removeChannel(Long channelId) {
        channelMap.remove(channelId);
    }

    public ChatChannel getChatChannel(Long channelId) {
        return channelMap.get(channelId);
    }

}
