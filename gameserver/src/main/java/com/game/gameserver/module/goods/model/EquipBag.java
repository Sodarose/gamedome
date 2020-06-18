package com.game.gameserver.module.goods.model;

import com.game.gameserver.module.goods.entity.Equip;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 玩家装备栏
 *
 * @author xuewenkang
 * @date 2020/6/7 10:26
 */
public class EquipBag  {
    /** 容量 */
    private int capacity = 6;
    /** 容器 key为格子Id value 为装备Id */
    private final Map<Integer, Equip> rawData;
    /** 读写锁 */
    private final ReentrantReadWriteLock lock;

    public EquipBag(){
        this.rawData = new ConcurrentHashMap<>(capacity);
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * 得到装备列表
     *
     * @param
     * @return java.util.List<com.game.gameserver.module.goods.entity.Equip>
     */
    public List<Equip> getEquipList(){
        return null;
    }

}
