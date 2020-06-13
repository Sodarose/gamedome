package com.game.gameserver.module.goods.dao;

import com.game.gameserver.module.goods.entity.Prop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 道具Dao
 *
 * @author xuewenkang
 * @date 2020/6/10 16:41
 */
@Repository
@Mapper
public interface PropMapper {

    /**
     * 获得道具列表
     *
     * @param playerId 用户Id
     * @param bagPack 背包类型
     * @return java.util.List<com.game.gameserver.module.goods.entity.Prop>
     */
    List<Prop> getPropList(int playerId,int bagPack);
}
