package com.game.gameserver.module.goods.model;


import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.entity.Goods;

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
    private int capacity;
    /** 容器 key为格子Id */
    private Map<Integer,Equip> rawData;
    private ReentrantReadWriteLock lock;

    public EquipBag(int capacity){
        this.capacity = capacity;
        this.rawData = new ConcurrentHashMap<>(capacity);
        this.lock = new ReentrantReadWriteLock();
    }


    public boolean put(Equip equip){
        return false;
    }

    public Equip take(int bagIndex,int equipId){
        return null;
    }

    public Equip take(int equipId){
        return null;
    }

    public List<Equip> getEquipList(){
        return null;
    }
}
