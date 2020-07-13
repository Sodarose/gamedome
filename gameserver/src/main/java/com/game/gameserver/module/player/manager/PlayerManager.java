package com.game.gameserver.module.player.manager;

import com.game.gameserver.event.Listener;
import com.game.gameserver.module.player.entity.Player;
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

    private final Map<Long, Player> playerObjectMap = new ConcurrentHashMap<>(1);

    public Player getPlayer(Long playerId) {
        return playerObjectMap.get(playerId);
    }

    public void putPlayerObject(Player player) {
        playerObjectMap.put(player.getId(), player);
    }

    public void update() {
        for (Map.Entry<Long, Player> entry : playerObjectMap.entrySet()) {
            entry.getValue().update();
        }
    }

    
}
