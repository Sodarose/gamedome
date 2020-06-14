package com.game.gameserver.module.goods.service;

import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.model.EquipBag;

import java.util.List;

/**
 * 装备接口
 *
 * @author xuewenkang
 * @date 2020/6/10 15:03
 */
public interface EquipService {
    /**
     * 根据角色ID 加载角色装备栏
     *
     * @param playerId
     * @return com.game.gameserver.module.goods.model.EquipBar
     */
    EquipBag loadEquipBar(int playerId);

    /**
     * 穿上装备
     *
     * @param playerId 玩家角色id
     * @param equipId  装备Id
     * @return void
     */
    void putEquip(int playerId, int equipId);

    /**
     * 脱下装备
     *
     * @param playerId 玩家角色id
     * @param equipId
     * @return void
     */
    void takeEquip(int playerId, int equipId);

    /**
     * 得到装备栏所有装备实体
     *
     * @param playerId
     * @return java.util.List<com.game.gameserver.module.goods.entity.Equip>
     */
    List<Equip> getEquipList(int playerId);

}
