package com.game.gameserver.module.cache.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 缓存管理器
 *
 * @author xuewenkang
 * @date 2020/6/15 12:27
 */
@Component
public class CacheManager {
    private final static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    public static CacheManager instance;

    

    public CacheManager(){
        instance = this;
    }
}
