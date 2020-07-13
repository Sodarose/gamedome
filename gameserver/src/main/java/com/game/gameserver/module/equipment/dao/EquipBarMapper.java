package com.game.gameserver.module.equipment.dao;

import com.game.gameserver.module.equipment.entity.EquipBarEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xuewenkang
 * @date 2020/7/12 17:03
 */
@Mapper
@Repository
public interface EquipBarMapper {
    int insert(EquipBarEntity equipBarEntity);
    EquipBarEntity select(long playerId);
    int update(EquipBarEntity equipBarEntity);
    int delete(long playerId);
}
