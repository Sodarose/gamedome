package com.game.gameserver.module.item.dao;

import com.game.gameserver.module.item.model.ItemModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 插入一个数据
     * @param itemModel 道具数据
     * @return java.lang.Integer
     */
    Integer insertItem(ItemModel itemModel);
}
