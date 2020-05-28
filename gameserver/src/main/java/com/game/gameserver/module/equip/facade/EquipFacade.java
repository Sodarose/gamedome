package com.game.gameserver.module.equip.facade;

import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.equip.entity.EquipBar;
import com.game.gameserver.module.equip.entity.Equip;

import java.util.List;

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
     * 查询装备
     * @param playerId
     * @param checkWay
     * @param bagId
     * @param bagIndex
     * @return com.game.gameserver.module.equip.entity.Equip
     */
    Equip checkItem(Integer playerId,int checkWay,Integer bagId,Integer bagIndex);

    /**
     * 根据道具ID 返回装备数据
     * @param itemId
     * @return com.game.gameserver.module.equip.entity.Equip
     */
    Equip getEquipByItemId(Integer itemId);
}
