package com.game.gameserver.module.player.manager;

import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.event.LoginEvent;
import com.game.gameserver.module.player.model.PlayerObject;
import com.sun.media.jfxmedia.events.PlayerEvent;
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

    public static PlayerManager instance ;

    public PlayerManager(){
        instance = this;
    }

    private final Map<Long, PlayerObject> playerObjectMap = new ConcurrentHashMap<>(1);

    public PlayerObject getPlayerObject(Long playerId) {
        return playerObjectMap.get(playerId);
    }

    public void putPlayerObject(PlayerObject playerObject){
        playerObjectMap.put(playerObject.getPlayer().getId(),playerObject);
    }
}
