package com.game.gameserver.module.item.service;

import com.game.gameserver.module.player.model.PlayerObject;
import com.game.protocol.ItemProtocol;

/**
 * @author xuewenkang
 * @date 2020/6/20 5:22
 */
public interface EquipService {
    /**
     * 获取装备栏数据
     *
     * @param playerObject
     * @return com.game.gameserver.module.item.entity.EquipBag
     */
    ItemProtocol.EquipBag getEquipBag(PlayerObject playerObject);

    /**
     * 脱下装备
     *
     * @param playerObject
     * @param equipId
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    ItemProtocol.ItemResult takeOffEquip(PlayerObject playerObject,Long equipId);

    /**
     * 穿上装备
     *
     * @param playerObject
     * @param equipId
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    ItemProtocol.ItemResult putUpEquip(PlayerObject playerObject,Long equipId);

    /**
     * 修理装备
     *
     * @param playerObject
     * @param equipId
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    ItemProtocol.ItemResult fixEquip(PlayerObject playerObject,Long equipId);

}
