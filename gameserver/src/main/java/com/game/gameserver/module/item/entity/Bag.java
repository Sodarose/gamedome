package com.game.gameserver.module.item.entity;

import com.game.gameserver.common.config.PropConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.item.type.ItemType;

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

    /**
     * 容量
     */
    private int capacity;
    private final int bagType;
    private final Item[] rawData;
    private final ReentrantReadWriteLock lock;

    public Bag(int capacity, int bagType) {
        this.capacity = capacity;
        this.bagType = bagType;
        this.rawData = new Item[capacity];
        this.lock = new ReentrantReadWriteLock();
    }

    public void initialize(List<Item> itemList) {
        for (Item item : itemList) {
            rawData[item.getBagIndex()] = item;
        }
    }

    public Item getItem(Long itemId) {
        for (Item item : rawData) {
            if (item != null && item.getId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }

    public boolean removeItem(Long itemId) {
        for (int i = 0; i < rawData.length; i++) {
            if (rawData[i] != null && rawData[i].getId().equals(itemId)) {
                rawData[i] = null;
                return true;
            }
        }
        return false;
    }


    public Lock getReadLock() {
        return lock.readLock();
    }

    public Lock getWriteLock() {
        return lock.writeLock();
    }

    public int getBagType() {
        return bagType;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean hasSpace(Item... items) {
        // 需要格子数
        int needGrid = 0;
        for (Item item : items) {
            // 如果是装备
            if (item.getItemType().equals(ItemType.EQUIP)) {
                needGrid += 1;
            }
            // 如果是道具 判断是否可以叠加
            if (item.getItemType().equals(ItemType.PROP)) {
                // 是否有同类道具
                PropConfig propConfig = StaticConfigManager.getInstance().getPropConfigMap().get(item.getItemId());
                if (propConfig == null) {
                    return false;
                }
                // 是否可叠加
                // 叠加后还剩多少 需要多少个格子
            }
        }
        return rawData.length + needGrid <= capacity;
    }

    public Item[] getRawData() {
        return rawData;
    }
}
