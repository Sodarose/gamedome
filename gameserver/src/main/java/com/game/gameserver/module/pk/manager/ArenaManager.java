package com.game.gameserver.module.pk.manager;

import com.game.gameserver.module.pk.entity.Arena;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 竞技场管理器
 *
 * @author xuewenkang
 * @date 2020/6/12 16:56
 */
@Component
public class ArenaManager {
    private final static Logger logger = LoggerFactory.getLogger(ArenaManager.class);

    /** 当前存储的竞技场 */
    private final Map<Integer, Arena> arenaMap = new ConcurrentHashMap<>(16);
}
