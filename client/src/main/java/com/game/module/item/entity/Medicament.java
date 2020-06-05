package com.game.gameserver.module.item.entity;

import com.game.gameserver.dictionary.entity.EquipData;
import com.game.gameserver.dictionary.entity.ItemData;
import com.game.gameserver.dictionary.entity.MedicamentData;
import com.game.gameserver.module.item.model.ItemModel;
import lombok.Data;

/**
 * 药剂
 * @author xuewenkang
 * @date 2020/6/3 11:07
 */
@Data
public class Medicament extends Item{
    private MedicamentData medicamentData;
    public Medicament(ItemModel itemModel, ItemData itemData, MedicamentData medicamentData){
        super(itemModel,itemData);
        this.medicamentData = medicamentData;
    }

    public void init(){

    }
}
