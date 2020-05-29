package com.game.gameserver.module.item.dao;

import com.game.gameserver.module.item.model.ItemModel;
import org.apache.ibatis.annotations.MapKey;
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
     * 从数据批量获取道具数据 以Map<Integer,ItemModel>的形式返回
     * @param itemIds 道具列表Id
     * @return java.util.Map<java.lang.Integer,com.game.gameserver.module.item.model.ItemModel>
     */
    @MapKey("id")
    Map<Integer, ItemModel> getItemMapByItemList(List<Integer> itemIds);

    /**
     *根据背包Id 获得背包内道具列表
     * @param bagId 背包id
     * @return java.util.Map<java.lang.Integer,com.game.gameserver.module.item.model.ItemModel>
     */
    List<ItemModel> getItemMapByBagId(Integer bagId);
}
