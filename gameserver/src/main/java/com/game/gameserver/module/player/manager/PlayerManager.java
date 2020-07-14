package com.game.gameserver.module.player.manager;

import com.game.gameserver.event.Listener;
import com.game.gameserver.module.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/9 11:08
 */
@Listener
@Component
public class PlayerManager {
    private final static Logger logger = LoggerFactory.getLogger(PlayerManager.class);

    public static PlayerManager instance;

    public PlayerManager() {
        instance = this;
    }

    /** 角色本地缓存 */
    private final static Map<Long, Player> LOCAL_PLAYER_MAP = new ConcurrentHashMap<>(1);

    public Player getPlayer(long playerId){
        return LOCAL_PLAYER_MAP.get(playerId);
    }

    public void putPlayer(long playerId, Player playerDomain){
        LOCAL_PLAYER_MAP.put(playerId,playerDomain);
    }

    public void removePlayer(long playerId){
        LOCAL_PLAYER_MAP.remove(playerId);
    }

    public Map<Long,Player> getAllPlayer(){
        return LOCAL_PLAYER_MAP;
    }
}
