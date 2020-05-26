package com.game.gameserver.module.equip.facade;

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
     * 返回装备栏 装备属性
     * @param equipId 装备ID
     * @param part 装备栏位置
     * @return com.game.gameserver.module.equip.entity.Equip
     */
    Equip getEquipByEquipId(Integer equipId,Integer part);

    /**
     * 卸下装备
     * @param equipId 装备ID
     * @param part 装备栏位置
     * @return void
     */
    void takeEquip(Integer equipId,Integer part);

    /**
     * 穿上装备
     * @param equipId 装备ID
     * @param bagIndex 装备在背包的位置 Cell Id
     * @return void
     */
    void putEquip(Integer equipId,Integer bagIndex);

    /**
     * 查看装备属性(包括未拥有)
     * @param dictEquipId 装备ID
     * @return com.game.gameserver.module.equip.entity.Equip
     */
    Equip searchEquipByDictEquipId(Integer dictEquipId);
}
