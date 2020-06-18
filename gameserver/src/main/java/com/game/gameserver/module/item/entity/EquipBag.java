package com.game.gameserver.module.item.entity;


import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 玩家装备栏
 *
 * @author xuewenkang
 * @date 2020/6/7 10:26
 */
public class EquipBag  {
    /** 数据容器 */
    private int capacity;
    private Equip[] rawData;
    private ReentrantReadWriteLock lock;

    public EquipBag(int capacity){
        this.capacity = capacity;
        this.rawData = new Equip[capacity];
        this.lock = new ReentrantReadWriteLock();
    }

    public void initialize(List<Equip> equipList){
        for(Equip equip:equipList){
            rawData[equip.getBagIndex()] = equip;
        }
    }

    public Lock getReadLock(){
        return lock.readLock();
    }

    public Lock getWriteLock(){
        return lock.writeLock();
    }
}
