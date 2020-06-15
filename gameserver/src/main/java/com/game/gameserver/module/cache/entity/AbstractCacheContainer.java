package com.game.gameserver.module.cache.entity;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象缓存容器
 *
 * @author xuewenkang
 * @date 2020/6/15 12:12
 */
public class AbstractCacheContainer<T> {
    /** 过期时间 */
    protected long expireTime = 10000;
    /** 保存到数据库的时间 */
    protected long saveTime = 5000;
    /** 缓存容器 */
    protected Map<Integer,T> rawData = new ConcurrentHashMap<>(16);
    /** 待更新容器 */
    protected Map<Integer,T> updateData = new ConcurrentHashMap<>(16);
}
