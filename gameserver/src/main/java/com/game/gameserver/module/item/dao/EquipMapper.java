package com.game.gameserver.module.item.dao;

import com.game.gameserver.module.item.entity.Equip;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 装备Dao
 *
 * @author xuewenkang
 * @date 2020/6/10 15:02
 */
@Repository
@Mapper
public interface EquipMapper {

    /**
     * 根据角色Id 获取角色装备
     *
     * @param playerId 用户Id
     * @param bagPack  背包
     * @return java.util.List<com.game.gameserver.module.goods.entity.EquipEntity>
     */
    List<Equip> getEquipEntityList(long playerId,int bagPack);
}
