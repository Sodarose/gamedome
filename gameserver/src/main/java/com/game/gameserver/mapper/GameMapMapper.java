package com.game.gameserver.mapper;

import com.game.entity.GameMap;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 */
@Repository
public interface GameMapMapper {

    /**
     * 查找数据库中的所有地图
     * @return 地图列表
     * */
    @MapKey("id")
    Map<Integer,GameMap> list();
}
