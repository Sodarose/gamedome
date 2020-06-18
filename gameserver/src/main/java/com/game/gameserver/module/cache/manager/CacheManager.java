package com.game.gameserver.module.cache.manager;

import com.game.gameserver.module.cache.entity.EquipCacheContainer;
import com.game.gameserver.module.cache.entity.PlayerCacheContainer;
import com.game.gameserver.module.cache.entity.PropCacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存管理器
 *
 * @author xuewenkang
 * @date 2020/6/15 12:27
 */
@Component
public class CacheManager {
    private final static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private final Map<Long, PlayerCacheContainer> playerCacheContainerMap = new ConcurrentHashMap<>();
    private final Map<Long, EquipCacheContainer> equipCacheContainerMap = new ConcurrentHashMap<>();
    private final Map<Long, PropCacheContainer> propCacheContainerMap = new ConcurrentHashMap<>();

    public static CacheManager instance;

    public CacheManager() {
        instance = this;
    }

    public PlayerCacheContainer getPlayerCacheContainer(Long playerId) {
        return playerCacheContainerMap.get(playerId);
    }


    public EquipCacheContainer getEquipCacheContainer(Long playerId) {
        return equipCacheContainerMap.get(playerId);
    }

    public PropCacheContainer getPropCacheContainer(Long playerId) {
        return propCacheContainerMap.get(playerId);
    }

}
