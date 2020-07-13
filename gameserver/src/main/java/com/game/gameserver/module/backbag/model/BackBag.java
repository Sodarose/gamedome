package com.game.gameserver.module.backbag.model;

import com.game.gameserver.module.backbag.entity.BackBagEntity;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 角色背包
 *
 * @author xuewenkang
 * @date 2020/7/2 21:39
 */
@Data
public class BackBag {
    /** 所有者 */
    private Long playerId;
    /** 背包容量 */
    private Integer capacity;
    /** 道具容器 */
    private Map<Integer,Item> itemMap = new ConcurrentHashMap<>();

    /** 读写锁 */
    private ReentrantReadWriteLock lock;

    public BackBag(){

    }

    public BackBag(BackBagEntity backBagEntity){
        this.playerId = backBagEntity.getPlayerId();
        this.capacity = backBagEntity.getCapacity();
        this.lock = new ReentrantReadWriteLock();
    }

    public Lock getReadLock(){
        return lock.readLock();
    }

    public Lock getWriteLock(){
        return lock.writeLock();
    }

    public boolean hasScape(){
        return itemMap.size()<capacity;
    }
}
