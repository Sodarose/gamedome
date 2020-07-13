package com.game.gameserver.module.friend.manager;

import com.game.gameserver.module.friend.model.UserFriend;
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
    private final static Map<Long, UserFriend> LOCAL_PLAYER_FRIEND_MAP = new ConcurrentHashMap<>();

    public UserFriend getUserFriend(long playerId){
        return LOCAL_PLAYER_FRIEND_MAP.get(playerId);
    }

    public void putUserFriend(long playerId, UserFriend playerFriend){
        LOCAL_PLAYER_FRIEND_MAP.put(playerId,playerFriend);
    }

    public void removeUserFriend(long playerId){
        LOCAL_PLAYER_FRIEND_MAP.remove(playerId);
    }
}
