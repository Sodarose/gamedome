package com.game.gameserver.module.item.service;

import com.game.gameserver.module.item.entity.EquipBag;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.protocol.ItemProtocol;

/**
 * @author kangkang
 */
public interface ItemService {

    /**
     * 获取玩家背包数据
     *
     * @param playerObject
     * @return com.game.protocol.ItemProtocol.PlayerBag
     */
    ItemProtocol.PlayerBag getPlayerBag(PlayerObject playerObject);

    /**
     * 使用道具
     *
     * @param playerObject
     * @param itemId
     * @param bagIndex
     * @param num
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    ItemProtocol.ItemResult useItem(PlayerObject playerObject,Long itemId,int bagIndex,int num);


    /**
     * 丢弃道具
     *
     * @param playerObject
     * @param itemId
     * @param bagIndex
     * @param num
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    ItemProtocol.ItemResult dropItem(PlayerObject playerObject,Long itemId,int bagIndex,int num);


    /**
     * 移动道具
     *
     * @param playerObject
     * @param itemId
     * @param bagIndex
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    ItemProtocol.ItemResult moveItem(PlayerObject playerObject,Long itemId,int bagIndex);

    /**
     * 整理背包
     *
     * @param playerObject
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    ItemProtocol.ItemResult clearBag(PlayerObject playerObject);
}
