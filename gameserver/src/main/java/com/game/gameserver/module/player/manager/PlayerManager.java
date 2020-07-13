package com.game.gameserver.module.player.manager;

import com.game.gameserver.event.Listener;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
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
    private final static Map<Long, Player> LOCAL_PLAYER_DOMAIN_MAP = new ConcurrentHashMap<>(1);
    /** 玩家角色列表缓存 */
    private final static Map<Long, List<Role>> LOCAL_USER_ROLE_MAP = new ConcurrentHashMap<>();

    public Player getPlayerDomain(long playerId){
        return LOCAL_PLAYER_DOMAIN_MAP.get(playerId);
    }

    public void putPlayerDomain(long playerId, Player playerDomain){
        LOCAL_PLAYER_DOMAIN_MAP.put(playerId,playerDomain);
    }

    public void removePlayerDomain(long playerId){
        LOCAL_PLAYER_DOMAIN_MAP.remove(playerId);
    }

}
