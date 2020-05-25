package com.game.gameserver.module.player.manager;

import com.game.gameserver.module.player.entity.PlayerEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/5/25 10:35
 */
@Component
public class PlayerManager {
    private Map<Integer, PlayerEntity> playerEntityMap = new ConcurrentHashMap<>(16);


}
