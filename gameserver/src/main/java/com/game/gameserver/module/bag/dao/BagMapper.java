package com.game.gameserver.module.bag.dao;

import com.game.gameserver.module.bag.model.BagModel;
import com.game.gameserver.module.bag.model.CellModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/27 9:36
 */
@Mapper
@Repository
public interface BagMapper {
    /**
     * 根据角色Id 获得角色背包数据
     * @param roleId 角色ID
     * @param type 背包类型{0:普通背包 1:道具快捷栏}
     * @return com.game.gameserver.module.bag.model.BagModel
     */
    BagModel getBagByRoleId(Integer roleId,Integer type);

    /**
     * 根据背包Id获得用户背包的内容数据
     * @param bagId 背包Id
     * @return java.util.List<com.game.gameserver.module.bag.model.CellModel>
     */
    List<CellModel> getCellByBagId(Integer bagId);
}
