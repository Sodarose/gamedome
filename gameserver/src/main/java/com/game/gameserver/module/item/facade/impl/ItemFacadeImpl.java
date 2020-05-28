package com.game.gameserver.module.item.facade.impl;

import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.equip.entity.Equip;
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
    private EquipFacade equipFacade;
    @Autowired
    private DictionaryManager dictionaryManager;
    @Autowired
    private PlayerManager playerManager;



    @Override
    public Item searchItemByItemId(Integer dictItemId) {
        return null;
    }

    @Override
    public Map<Integer, Item> getItemMapByItemList(List<Integer> itemIds) {
        Map<Integer, ItemModel> itemModelMap = itemMapper.getItemMapByItemList(itemIds);
        Map<Integer,Item> itemMap = new HashMap<>(itemModelMap.size());
        for(Map.Entry<Integer,ItemModel> entry:itemModelMap.entrySet()){
            Item item = createItemByItemModel(entry.getValue());
            itemMap.put(item.getId(),item);
        }
        return itemMap;
    }

    /**
     * 查看角色背包中的道具
     *
     * @param itemId 道具Id
     * @return com.game.gameserver.module.item.entity.Item
     */
    @Override
    public DictItem checkItem(Integer itemId) {
        return null;
    }

    /**
     * 玩家使用道具
     *
     * @param playerId 玩家ID
     * @param bagId    背包ID
     * @param bagIndex 道具在背包的位置
     * @param itemId   道具ID
     * @return com.game.protocol.ItemProtocol.UseItemNotify
     */
    @Override
    public ItemProtocol.UseItemNotify useItem(Integer playerId, Integer bagId, Integer bagIndex, Integer itemId) {
        Player player = playerManager.getPlayer(playerId);
        if(player==null){
            return null;
        }
        Bag bag = player.getBag();
        Item item = bag.getItem(bagIndex,itemId);
        switch (item.getItemType()){
            case ItemType.EQUIP:
                //
                break;
            case ItemType.CONSUMABLES:
                useConsumables(item);
                break;
            default:{}
        }
        return null;
    }

    private void useConsumables(Item item){

    }


    /**
     * 根据ItemModel 生成 Item
     * @param itemModel
     * @return com.game.gameserver.module.item.entity.Item
     */
    private Item createItemByItemModel(ItemModel itemModel){
        if(itemModel.getItemType().equals(ItemType.EQUIP)){
            return equipFacade.getEquipByItemId(itemModel.getId());
        }
        Item item = new Item();
        item.setId(itemModel.getId());
        item.setRoleId(itemModel.getRoleId());
        item.setItemType(itemModel.getItemType());
        item.setDictItem(dictionaryManager.getDictItemById(itemModel.getItemId()));
        return item;
    }
}
