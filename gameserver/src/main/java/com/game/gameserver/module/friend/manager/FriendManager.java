package com.game.gameserver.module.friend.manager;

import com.game.gameserver.event.Listener;
import com.game.gameserver.module.friend.model.PlayerFriend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/7/13 17:35
 */
@Component
public class FriendManager {
    private final static Logger logger = LoggerFactory.getLogger(FriendManager.class);

    /** 本地好友缓存 */
    private final static Map<Long, PlayerFriend> LOCAL_PLAYER_FRIEND_MAP = new ConcurrentHashMap<>();

    public PlayerFriend getPlayerFriend(long playerId){
        return LOCAL_PLAYER_FRIEND_MAP.get(playerId);
    }

    public void putPlayerFriend(long playerId, PlayerFriend playerFriend){
        LOCAL_PLAYER_FRIEND_MAP.put(playerId,playerFriend);
    }

    public void removePlayerFriend(long playerId){
        LOCAL_PLAYER_FRIEND_MAP.remove(playerId);
    }
}
