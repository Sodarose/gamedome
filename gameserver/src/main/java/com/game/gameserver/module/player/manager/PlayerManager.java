package com.game.gameserver.module.player.manager;

import com.game.gameserver.module.player.object.PlayerObject;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class PlayerManager {
    private Map<Integer, PlayerObject> playerObjectMap = new ConcurrentHashMap<>(1);
    public void addPlayerObject(PlayerObject playerObject){
        playerObjectMap.put(playerObject.getPlayerEntity().getId(),playerObject);
    }

    public void removePlayerObject(Integer playerId){
        playerObjectMap.remove(playerId);
    }

    public PlayerObject getPlayerObject(Integer playerId){
        return playerObjectMap.get(playerId);
    }
}
