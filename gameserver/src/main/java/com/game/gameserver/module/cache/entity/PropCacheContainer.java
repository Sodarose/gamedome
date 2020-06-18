package com.game.gameserver.module.cache.entity;

import com.game.gameserver.module.item.entity.Prop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/17 13:20
 */
public class PropCacheContainer implements CacheContainer<Prop> {

    private final Map<Long, Prop> rawData = new ConcurrentHashMap<>();

    public boolean add(Prop prop) {
        synchronized (rawData) {
            if (rawData.containsKey(prop.getId())) {
                return false;
            }
            rawData.put(prop.getId(), prop);
            return true;
        }
    }

    public boolean remove(Prop prop) {
        synchronized (rawData) {
            if (!rawData.containsKey(prop.getId())) {
                return false;
            }
            rawData.remove(prop.getId());
            return true;
        }
    }

    public Prop getEquip(Long propId) {
        return rawData.get(propId);
    }

    public Map<Long, Prop> getRawData() {
        return rawData;
    }
}
