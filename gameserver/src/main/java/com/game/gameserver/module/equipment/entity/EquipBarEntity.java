package com.game.gameserver.module.equipment.entity;

import lombok.Data;

/**
 *
 *
 * @author xuewenkang
 * @date 2020/7/12 17:52
 */
@Data
public class EquipBarEntity {
    private long playerId;
    private String items;

    public EquipBarEntity(){

    }

    public EquipBarEntity(long playerId,String items){
        this.playerId = playerId;
        this.items = items;
    }
}
