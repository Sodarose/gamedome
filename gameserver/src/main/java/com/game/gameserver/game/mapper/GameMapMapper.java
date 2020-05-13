package com.game.gameserver.game.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

/**
 * 游戏Mapper
 * @author xuewenkang
 */
@Repository
public interface GameMapMapper {

    /**
     * 查找数据库中的所有地图
     * @return 地图列表
     * */
    @MapKey("id")
    java.util.Map list();
}
