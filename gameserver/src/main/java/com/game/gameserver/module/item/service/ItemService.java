package com.game.gameserver.module.item.service;

import com.game.gameserver.module.item.dao.ItemMapper;
import com.game.gameserver.module.item.entity.BagEntity;
import com.game.gameserver.module.item.entity.EquipBarEntity;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.model.ItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 物品Service
 * */
@Component
public class ItemService {
    private final static int BAG = 0;
    private final static int EQUIP_BAR = 1;
    private final static int CONSUMABLE = 0;
    private final static int EQUIP = 1;


    @Autowired
    private ItemMapper itemMapper;

    /**
     * 读取用户背包数据
     * */
    public BagEntity loadBagItem(Integer playerId) {
        // 获取背包中的数据
        List<ItemModel> itemList = itemMapper.getItemList(playerId,BAG);
        List<Item> items = new ArrayList<>();
        for(ItemModel itemModel:itemList){
            Item item = new Item(itemModel);
            }
        Item item = null;
        items.add(item);

        BagEntity bagEntity = new BagEntity();

        return bagEntity;
    }

    /**
     * 读取用户装备栏数据
     * */
    public EquipBarEntity loadEquipBar(Integer playerId) {
        List<ItemModel> itemList = itemMapper.getItemList(playerId,EQUIP_BAR);
        EquipBarEntity equipBarEntity = new EquipBarEntity();
        List<Item> items = new ArrayList<>();
        for(ItemModel itemModel:itemList){
            Item item = new Item(itemModel);

        }
        equipBarEntity.init(items);
        return equipBarEntity;
    }


}
