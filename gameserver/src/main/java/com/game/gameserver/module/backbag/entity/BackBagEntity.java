package com.game.gameserver.module.backbag.entity;

import com.game.gameserver.module.backbag.model.BackBag;
import lombok.Data;

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
    /** 背包中的道具 */
    private String items;

    public BackBagEntity(){

    }

    public BackBagEntity(long playerId,int capacity,String items){
        this.playerId = playerId;
        this.capacity = capacity;
        this.items = items;
    }
}
