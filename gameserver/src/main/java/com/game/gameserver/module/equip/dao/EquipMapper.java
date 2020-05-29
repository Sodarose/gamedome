package com.game.gameserver.module.equip.dao;

import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.equip.model.EquipModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/25 17:58
 */
@Mapper
@Repository
public interface EquipMapper {
    /**
     * 根据角色Id查找已经装备在角色身上的装备
     * @param roleId
     * @return java.util.List<com.game.gameserver.module.equip.model.Equip>
     */
    List<EquipModel> getIsEquipmentEquipByRoleId(Integer roleId);

    /**
     * 根据bagId获得在背包中的装备
     * @param bagId
     * @return java.util.List<com.game.gameserver.module.equip.model.EquipModel>
     */
    List<EquipModel> getEquipListByBagId(Integer bagId);
}
