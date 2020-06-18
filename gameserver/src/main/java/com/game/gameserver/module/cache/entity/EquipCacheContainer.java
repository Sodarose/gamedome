package com.game.gameserver.module.cache.entity;

import com.game.gameserver.module.item.entity.Equip;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/17 13:17
 */
public class EquipCacheContainer implements CacheContainer<Equip> {

    private final Map<Long, Equip> rawData = new ConcurrentHashMap<>();

    public boolean add(Equip goods) {
        synchronized (rawData) {
            if (rawData.containsKey(goods.getId())) {
                return false;
            }
            rawData.put(goods.getId(), goods);
            return true;
        }
    }

    public boolean remove(Equip equip) {
        synchronized (rawData) {
            if (!rawData.containsKey(equip.getId())) {
                return false;
            }
            rawData.remove(equip.getId());
            return true;
        }
    }

    public Equip getEquip(Long equipId) {
        return rawData.get(equipId);
    }

    public Map<Long, Equip> getRawData() {
        return rawData;
    }
}
