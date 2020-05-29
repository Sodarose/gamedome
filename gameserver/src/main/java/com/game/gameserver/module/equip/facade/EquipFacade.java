package com.game.gameserver.module.equip.facade;

import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.equip.entity.EquipBar;
import com.game.gameserver.module.equip.entity.Equip;

import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/25 17:59
 */
public interface EquipFacade {
    /**
     * 根据角色ID 返回已经装备上的了的装备
     * @param roleId
     * @return java.util.List<com.game.gameserver.module.equip.entity.EquipEntity>
     */
    List<Equip> getEquipListByRoleId(Integer roleId);

    /**
     * 根据角色ID 返回角色装备栏
     * @param roleId
     * @return com.game.gameserver.module.equip.entity.EquipBar
     */
    EquipBar getEquipBarByRoleId(Integer roleId);

    /**
     * 获得背包内的装备列表
     * @param bagId
     * @return java.util.Map<java.lang.Integer,com.game.gameserver.module.equip.entity.Equip>
     */
    List<Equip> getEquipMapByBagId(Integer bagId);


    /**
     * 穿上装备
     * @param playerId
     * @param equipId
     * @return void
     */
    void putEquip(Integer playerId,Integer equipId);

}
