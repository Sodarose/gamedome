package com.game.gameserver.module.item.entity;

import com.game.gameserver.module.player.model.Property;
import lombok.Data;

import java.util.List;

/**
 * 装备栏
 * @author xuewenkang
 * @date 2020/5/26 10:39
 */
@Data
public class EquipBar {
    /** 最大可装备数量 */
    private final static int MAX_EQUIP_LENGTH = 13;
    private Equip[] equips = new Equip[MAX_EQUIP_LENGTH];
    public EquipBar(){

    }

    public void init(List<Equip> equipList){
        for(Equip equip:equipList){
            equips[equip.getDictEquip().getPart()]= equip;
        }
    }

}
