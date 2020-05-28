package com.game.gameserver.module.item.facade;

import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.item.entity.Item;
import com.game.protocol.ItemProtocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/25 17:43
 */

public interface ItemFacade {


    /***
     * 搜索道具信息
     * @param dictItemId 道具字典ID
     * @return com.game.gameserver.module.item.entity.Item
     */
    Item searchItemByItemId(Integer dictItemId);

    /**
     * 批量获取道具信息 并以Map的形式返回
     * @param itemIds itemId 列表
     * @return java.util.Map<java.lang.Integer,com.game.gameserver.module.item.entity.Item>
     */
    Map<Integer,Item> getItemMapByItemList(List<Integer> itemIds);

    /**
     * 查看角色背包中的道具
     * @param itemId 道具Id
     * @return com.game.gameserver.module.item.entity.Item
     */
    DictItem checkItem(Integer itemId);

    /**
     * 玩家使用道具
     * @param playerId 玩家ID
     * @param bagId 背包ID
     * @param bagIndex 道具在背包的位置
     * @param itemId 道具ID
     * @return com.game.protocol.ItemProtocol.UseItemNotify
     */
    ItemProtocol.UseItemNotify useItem(Integer playerId,Integer bagId,Integer bagIndex,Integer itemId);
}
