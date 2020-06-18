package com.game.gameserver.module.item.entity;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用户背包
 *
 * @author xuewenkang
 * @date 2020/6/10 10:28
 */
public class Bag {

    /** 容量 */
    private int capacity;
    private final int bagType;
    private Item[] rawData;
    private final ReentrantReadWriteLock lock;

    public Bag(int capacity, int bagType){
        this.capacity = capacity;
        this.bagType = bagType;
        this.rawData = new Item[capacity];
        this.lock = new ReentrantReadWriteLock();
    }

    public void initialize(List<Item> itemList){
        for(Item item:itemList){
            rawData[item.getBagIndex()] = item;
        }
    }

    public Lock getReadLock(){
        return lock.readLock();
    }

    public Lock getWriteLock(){
        return lock.writeLock();
    }

    public int getBagType(){
        return bagType;
    }

    public int getCapacity() {
        return capacity;
    }
}
