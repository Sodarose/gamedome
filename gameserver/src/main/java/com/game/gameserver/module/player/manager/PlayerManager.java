package com.game.gameserver.module.player.manager;

import com.game.gameserver.module.player.entity.Player;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/5/25 10:35
 */
@Component
public class PlayerManager {
    private Map<Integer, Player> playerEntityMap = new ConcurrentHashMap<>(16);
    public void putPlayer(Player player){
        playerEntityMap.put(player.getId(), player);
    }

    public Player getPlayer(Integer id){
        return playerEntityMap.get(id);
    }
}
