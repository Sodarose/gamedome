package com.game.gameserver.module.item.entity;

import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.item.model.ItemModel;
import lombok.Data;

/**
 * 物品通用属性
 * */
@Data
public class Item {
    private ItemModel itemModel;
    private DictItem dictItem;

    public Item(ItemModel itemModel){
        this.itemModel = itemModel;
    }
}
