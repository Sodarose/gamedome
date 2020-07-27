package com.game.gameserver.module.guild.model;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.guild.entity.GuildWarehouseEntity;
import com.game.gameserver.module.item.model.Item;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 公会仓库
 *
 * @author xuewenkang
 * @date 2020/7/14 11:32
 */
@Data
public class GuildWarehouse implements Serializable {
    /**
     * 工会Id
     */
    private long guild;

    /**
     * 仓库容量
     */
    private int capacity;

    /**
     * 仓库道具表 位置/道具
     */
    private Map<Integer, Item> itemMap;

    /**
     * 读写锁
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public GuildWarehouse() {

    }

    public GuildWarehouse(long guild, int capacity) {
        this.guild = guild;
        this.capacity = capacity;
        this.itemMap = new ConcurrentHashMap<>();
    }


    public GuildWarehouse(GuildWarehouseEntity guildWarehouseEntity) {
        this.guild = guildWarehouseEntity.getGuildId();
        this.capacity = guildWarehouseEntity.getCapacity();
        this.itemMap = new ConcurrentHashMap<>();
    }

    public Lock getWriteLock() {
        return lock.writeLock();
    }

    public Lock getReadLock() {
        return lock.readLock();
    }

    public boolean hasSpace(Item item) {
        // 背包还有空位 直接返回
        if (itemMap.size() < capacity) {
            return true;
        } else {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap()
                    .get(item.getItemConfigId());
            // 是可叠加类型的么 不是直接返回
            if (!itemConfig.isOverlap()) {
                return false;
            }
            // 是可叠加类型 在仓库中找到同种物品 找到直接返回true
            for (Map.Entry<Integer, Item> entry : itemMap.entrySet()) {
                if(entry.getValue().getItemConfigId().equals(item.getItemConfigId())){
                    return true;
                }
            }
            return false;
        }
    }
}
