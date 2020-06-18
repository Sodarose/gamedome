package com.game.gameserver.module.chat.manager;

import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.chat.entity.ChatChannel;
import com.game.gameserver.module.chat.type.ChannelType;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.event.LoginEvent;
import com.game.gameserver.module.player.event.LogoutEvent;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.event.CreateTeamEvent;
import com.game.gameserver.util.GameUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

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
     * @param playerObject
     * @param channelId
     * @return void
     */
    public void entryChannel(PlayerObject playerObject, Long channelId) {
        ChatChannel chatChannel = channelMap.get(channelId);
        if (chatChannel == null) {
            return;
        }
        chatChannel.entry(playerObject.getPlayer().getId());
    }

    /**
     * 退出频道
     *
     * @param playerObject
     * @param channelId
     * @return void
     */
    public void exitChannel(PlayerObject playerObject, Long channelId) {
        ChatChannel chatChannel = channelMap.get(channelId);
        if (channelId == null) {
            return;
        }
        chatChannel.exit(playerObject.getPlayer().getId());
    }

    /**
     * 用户登录时 加入频道
     *
     * @param loginEvent
     * @return void
     */
    @EventHandler(type = EventType.LOGIN)
    public void entryChannelByLogin(LoginEvent loginEvent) {
        logger.info("{} 进入世界频道",loginEvent.getPlayerId());
        PlayerObject playerObject = PlayerManager
                .instance.getPlayerObject(loginEvent.getPlayerId());
        if(playerObject==null){
            return;
        }
        // 将用户注册进入频道
        ChatChannel worldChannel = channelMap.get(0L);
        worldChannel.entry(loginEvent.getPlayerId());
        // 将频道写入用户临时数据中
        playerObject.getPlayerChannelMap().put(ChannelType.WORLD_CHAT,0L);
    }

    /**
     * 当用户登出时 退出所有频道
     *
     * @param logoutEvent
     * @return void
     */
    @EventHandler(type = EventType.LOG_OUT)
    public void exitChannelByLogout(LogoutEvent logoutEvent) {
        logger.info("{} 退出世界频道",logoutEvent.getPlayerId());
        ChatChannel worldChannel = channelMap.get(0L);
        worldChannel.exit(logoutEvent.getPlayerId());
    }

    public void removeChannel(Long channelId) {
        channelMap.remove(channelId);
    }

    public ChatChannel getChatChannel(Long channelId) {
        return channelMap.get(channelId);
    }

}
