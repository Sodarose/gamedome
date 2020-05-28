package com.game.gameserver.module.equip.entity;

import com.game.gameserver.dictionary.dict.DictEquip;
import com.game.gameserver.dictionary.dict.DictRoleLevelProperty;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.Property;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/25 17:59
 */
@Data
public class Equip extends Item {
    private Integer id;
    private Integer durability;
    private Integer equipment;
    private DictEquip dictEquip;

    @Override
    public void setId(Integer id){
        super.setId(id);
        this.id = id;
    }
}
