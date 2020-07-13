package com.game.gameserver.module.equipment.model;

import com.game.gameserver.module.backbag.entity.BackBagEntity;
import com.game.gameserver.module.item.model.Item;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 装备栏
 *
 * @author xuewenkang
 * @date 2020/7/7 3:48
 */
public class PlayerEquipBar  {

    private final Map<Integer, Item> equipMap;

    public PlayerEquipBar() {
        this.equipMap = new ConcurrentHashMap<>();
    }

    public Map<Integer, Item> getEquipMap() {
        return equipMap;
    }
}
