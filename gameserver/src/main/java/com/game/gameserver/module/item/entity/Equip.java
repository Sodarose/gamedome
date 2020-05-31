package com.game.gameserver.module.item.entity;

import com.game.gameserver.dictionary.dict.DictEquip;
import com.game.gameserver.module.item.model.EquipModel;
import lombok.Data;

/**
 * 装备
 * */
@Data
public class Equip extends Item {
    private EquipModel equipModel;
    private DictEquip dictEquip;

    public Equip(EquipModel equipModel){
        this.equipModel = equipModel;
    }

}
