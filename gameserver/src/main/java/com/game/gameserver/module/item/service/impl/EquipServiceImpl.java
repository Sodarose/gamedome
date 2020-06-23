package com.game.gameserver.module.item.service.impl;

import com.game.gameserver.module.item.service.EquipService;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.protocol.ItemProtocol;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/6/20 5:23
 */
@Service
public class EquipServiceImpl implements EquipService {
    /**
     * 获取装备栏数据
     *
     * @param playerObject
     * @return com.game.gameserver.module.item.entity.EquipBag
     */
    @Override
    public ItemProtocol.EquipBag getEquipBag(PlayerObject playerObject) {
        return null;
    }

    /**
     * 脱下装备
     *
     * @param playerObject
     * @param equipId
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    @Override
    public ItemProtocol.ItemResult takeOffEquip(PlayerObject playerObject, Long equipId) {
        return null;
    }

    /**
     * 穿上装备
     *
     * @param playerObject
     * @param equipId
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    @Override
    public ItemProtocol.ItemResult putUpEquip(PlayerObject playerObject, Long equipId) {
        return null;
    }

    /**
     * 修理装备
     *
     * @param playerObject
     * @param equipId
     * @return com.game.protocol.ItemProtocol.ItemResult
     */
    @Override
    public ItemProtocol.ItemResult fixEquip(PlayerObject playerObject, Long equipId) {
        return null;
    }
}
