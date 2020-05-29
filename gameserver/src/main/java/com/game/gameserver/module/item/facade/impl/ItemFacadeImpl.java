package com.game.gameserver.module.item.facade.impl;

import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.equip.facade.EquipFacade;
import com.game.gameserver.module.item.dao.ItemMapper;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.facade.ItemFacade;
import com.game.gameserver.module.item.model.ItemModel;
import com.game.gameserver.module.item.model.ItemType;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.protocol.ItemProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/25 17:43
 */
@Service
public class ItemFacadeImpl implements ItemFacade {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private DictionaryManager dictionaryManager;

    /**
     * 根据背包Id 获得背包中的道具
     *
     * @param bagId 背包Id
     * @return java.util.Map<java.lang.Integer, com.game.gameserver.module.item.entity.Item>
     */
    @Override
    public List<Item> getItemMapByBagId(Integer bagId) {
        List<ItemModel> itemModelList = itemMapper.getItemMapByBagId(bagId);
        List<Item> itemList = new ArrayList<>();
        for(ItemModel itemModel:itemModelList){
            itemList.add(createItemByItemModel(itemModel));
        }
        return itemList;
    }

    private Item createItemByItemModel(ItemModel itemModel){
        Item item = new Item();
        item.setId(itemModel.getId());
        item.setDictItem(dictionaryManager.getDictItemById(itemModel.getItemId()));
        item.setItemType(itemModel.getItemType());
        item.setRoleId(itemModel.getRoleId());
        item.setBagId(itemModel.getBagId());
        item.setBagIndex(itemModel.getBagIndex());
        item.setItemCount(itemModel.getItemCount());
        return item;
    }
}
