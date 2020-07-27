package com.game.gameserver.module.backbag.entity;

import com.game.gameserver.module.backbag.model.BackBag;
import com.game.gameserver.module.item.model.Item;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/7/10 16:26
 */
@Data
public class BackBagEntity {

    /** 所有者 */
    private Long playerId;
    /** 背包容量 */
    private Integer capacity;
    /** 背吧中的道具 */
    private Map<Integer, Item> itemMap = new ConcurrentHashMap<>();

    public BackBagEntity(){

    }

    public BackBagEntity(long playerId,int capacity){
        this.playerId = playerId;
        this.capacity = capacity;
    }
}
