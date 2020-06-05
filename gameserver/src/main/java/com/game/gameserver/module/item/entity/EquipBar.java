package com.game.gameserver.module.item.entity;

import lombok.Data;

import java.util.List;

/**
 * 装备栏
 * @author xuewenkang
 * @date 2020/5/26 10:39
 */
@Data
public class EquipBarEntity {
    /** 最大可装备数量 */
    private final static int MAX_EQUIP_LENGTH = 13;
    private Equip[] equips = new Equip[6];

    public EquipBarEntity(){

    }

    public void init(List<Equip> equipList){
        for(Equip equip:equipList){
            equips[equip.getItemModel().getBagIndex()] = equip;
        }
    }

    public Equip[] getEquips(){
        return equips;
    }
}
