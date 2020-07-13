package com.game.gameserver.module.backbag.model;

import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用户背包
 *
 * @author xuewenkang
 * @date 2020/7/2 21:39
 */
public class PlayerBackBag {
    /** 背包容量 */
    private int capacity;
    /** 背包容器 */
    private Map<Integer,Item> itemMap;
    /** 读写锁 */
    private final ReentrantReadWriteLock lock;

    public PlayerBackBag(int capacity){
        this.capacity = capacity;
        this.itemMap = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void initialize(List<Item> itemList){
        for(Item item:itemList){
            itemMap.put(item.getBagIndex(),item);
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public Map<Integer, Item> getItemMap() {
        return itemMap;
    }

    public Lock getReadLock(){
        return lock.readLock();
    }

    public Lock getWriteLock(){
        return lock.writeLock();
    }

}
