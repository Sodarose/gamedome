package com.game.gameserver.module.item.dao;

import com.game.gameserver.module.item.model.EquipModel;
import com.game.gameserver.module.item.model.ItemModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/25 17:49
 */
@Repository
@Mapper
public interface ItemMapper {
    /**
     * 根据角色Id 获得角色背包中的道具
     * @param playerId 角色Id
     * @param place 道具保存的位置
     * @return List<ItemModel> 道具数据列表
     * */
    List<ItemModel> getItemList(Integer playerId, Integer place);

    List<EquipModel> getEquipList(Integer playerId, Integer place);

}
