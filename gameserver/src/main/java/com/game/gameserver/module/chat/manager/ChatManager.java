package com.game.gameserver.module.chat.manager;

import com.game.gameserver.module.chat.entity.ChatChannel;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.util.GameUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * @author xuewenkang
 * @date 2020/6/15 16:26
 */
@Component
public class ChatManager {
    private final static Logger logger = LoggerFactory.getLogger(ChatManager.class);
    private final Map<Long, ChatChannel> channelMap = new ConcurrentHashMap<>(16);

    public static ChatManager instance;

    public ChatManager(){
        instance = this;
    }

    /**
     * 初始化部分频道
     * */
    public void initialize(){
        logger.info("初始化部分聊天频道");
        // 创建世界频道
        ChatChannel worldChatChannel = new ChatChannel(0L);
        channelMap.put(worldChatChannel.getChanelId(),worldChatChannel);
    }

    public Long createChatChannel(){
        ChatChannel chatChannel = new ChatChannel(GameUUID.getInstance().generate());
        channelMap.put(chatChannel.getChanelId(),chatChannel);
        return chatChannel.getChanelId();
    }

    /**
     * 进入频道
     *
     * @param playerObject
     * @param channelId
     * @return void
     */
    public void entryChannel(PlayerObject playerObject,Long channelId){
        ChatChannel chatChannel = channelMap.get(channelId);
        if(chatChannel==null){
            return;
        }
        Lock lock = chatChannel.getWriteLock();
        lock.lock();
        try{
            List<Long> players = chatChannel.getPlayers();
            boolean exit = players.contains(playerObject.getPlayer().getId());
            if(exit){
                return;
            }
            players.add(playerObject.getPlayer().getId());
        }finally {
            lock.unlock();
        }
    }

    /**
     * 退出频道
     *
     * @param playerObject
     * @param channelId
     * @return void
     */
    public void exitChannel(PlayerObject playerObject,Long channelId){
        ChatChannel chatChannel = channelMap.get(channelId);
        if(channelId==null){
            return;
        }
        Lock lock = chatChannel.getWriteLock();
        lock.lock();
        try{
            List<Long> players = chatChannel.getPlayers();
            boolean exit = players.contains(playerObject.getPlayer().getId());
            if(!exit){
                return;
            }
            players.remove(playerObject.getPlayer().getId());
        }finally {
            lock.unlock();
        }
    }


    /**
     * 定时移除已经没有成员存在的频道
     *
     * @param
     * @return void
     */
    public void removeChannel(){

    }

    public void removeChannel(Long channelId){
        channelMap.remove(channelId);
    }

    public ChatChannel getChatChannel(Long channelId){
        return channelMap.get(channelId);
    }

}
