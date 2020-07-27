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
public class BackBag extends BackBagEntity {

    public BackBag() {

    }

    public boolean hasScape() {
        return this.getItemMap().size() < this.getCapacity();
    }

    public boolean hasItem(Item item) {
        for (Map.Entry<Integer, Item> entry : this.getItemMap().entrySet()) {
            if (entry.getValue().getItemConfigId().equals(item.getItemConfigId())) {
                return true;
            }
        }
        return false;
    }
}
