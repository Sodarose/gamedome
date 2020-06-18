package com.game.gameserver.module.cache.entity;

import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.player.model.PlayerObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/17 13:19
 */
public class PlayerCacheContainer implements CacheContainer<PlayerObject> {

    private final Map<Long, PlayerObject> rawData = new ConcurrentHashMap<>();

    public boolean add(PlayerObject playerObject) {
        synchronized (rawData) {
            if (rawData.containsKey(playerObject.getPlayer().getId())) {
                return false;
            }
            rawData.put(playerObject.getPlayer().getId(), playerObject);
            return true;
        }
    }

    public boolean remove(PlayerObject playerObject) {
        synchronized (rawData) {
            if (rawData.containsKey(playerObject.getPlayer().getId())) {
                return false;
            }
            rawData.put(playerObject.getPlayer().getId(), playerObject);
            return true;
        }
    }

    public PlayerObject getPlayerObject(Long playerId) {
        return rawData.get(playerId);
    }

    public Map<Long, PlayerObject> getRawData() {
        return rawData;
    }
}
