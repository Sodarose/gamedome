package com.game.gameserver.module.equipment.entity;

import com.game.gameserver.module.item.model.Item;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author xuewenkang
 * @date 2020/7/12 17:52
 */
@Data
public class EquipBarEntity {
    private long playerId;
    /** 背吧中的道具 */
    private Map<Integer, Item> equipMap = new ConcurrentHashMap<>();

    public EquipBarEntity(){

    }

    public EquipBarEntity(long playerId,String items){
        this.playerId = playerId;
    }
}
