package com.game.gameserver.module.item.service;

import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.dictionary.dict.DictEquip;
import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.item.dao.ItemMapper;
import com.game.gameserver.module.item.entity.Bag;
import com.game.gameserver.module.item.entity.Equip;
import com.game.gameserver.module.item.entity.EquipBar;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.model.EquipModel;
import com.game.gameserver.module.item.model.ItemModel;
import org.apache.commons.collections4.queue.PredicatedQueue;
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
    @Autowired
    private DictionaryManager dictionaryManager;

    /**
     * 读取用户背包数据
     * */
    public Bag loadBagItem(Integer playerId){
        List<EquipModel> equipModels = itemMapper.getEquipList(playerId,BAG); // 获得背包中的装备数据
        List<ItemModel> itemModels = itemMapper.getItemList(playerId,BAG); // 获得背包中的道具数据
        List<Item> items = new ArrayList<>();
        for(EquipModel equipModel:equipModels){
            Equip equip = new Equip(equipModel);
            DictItem dictItem = dictionaryManager.getDictItemById(equipModel.getItemId());
            DictEquip dictEquip = dictionaryManager.getDictEquipById(dictItem.getAttachId());
            equip.setDictItem(dictItem);
            equip.setDictEquip(dictEquip);
            items.add(equip);
        }

        for(ItemModel itemModel:itemModels){
            Item item = new Item(itemModel);
            DictItem dictItem = dictionaryManager.getDictItemById(itemModel.getItemId());
            item.setDictItem(dictItem);
            items.add(item);
        }
        Bag bag = new Bag();
        bag.init(items);
        return bag;
    }



    /**
     * 读取用户装备栏数据
     * */
    public EquipBar loadEquipBar(Integer playerId){
        EquipBar equipBar = new EquipBar();
        List<EquipModel> equipModels = itemMapper.getEquipList(playerId,EQUIP_BAR);
        List<Equip> equips = new ArrayList<>();
        for(EquipModel equipModel:equipModels){
            Equip equip = new Equip(equipModel);
            DictItem dictItem = dictionaryManager.getDictItemById(equipModel.getItemId());
            DictEquip dictEquip = dictionaryManager.getDictEquipById(dictItem.getAttachId());
            equip.setDictItem(dictItem);
            equip.setDictEquip(dictEquip);
            equips.add(equip);
        }
        equipBar.init(equips);
        return equipBar;
    }

}
