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
    /**
     * 根据背包Id 获得背包中的道具
     * @param bagId 背包Id
     * @return java.util.Map<java.lang.Integer,com.game.gameserver.module.item.entity.Item>
     */
    List<Item> getItemMapByBagId(Integer bagId);
}
