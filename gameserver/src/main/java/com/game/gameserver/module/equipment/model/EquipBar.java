package com.game.gameserver.module.equipment.model;

import com.game.gameserver.module.backbag.entity.BackBagEntity;
import com.game.gameserver.module.equipment.entity.EquipBarEntity;
import com.game.gameserver.module.item.model.Item;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 装备栏
 *
 * @author xuewenkang
 * @date 2020/7/7 3:48
 */
@Data
public class EquipBar {
    private long playerId;

    private final Map<Integer, Item> equipMap;

    public EquipBar(){
        this.equipMap = new ConcurrentHashMap<>();
    }

    public EquipBar(EquipBarEntity equipBarEntity) {
        this.playerId = equipBarEntity.getPlayerId();
        this.equipMap = new ConcurrentHashMap<>();
    }

    public Map<Integer, Item> getEquipMap() {
        return equipMap;
    }
}
