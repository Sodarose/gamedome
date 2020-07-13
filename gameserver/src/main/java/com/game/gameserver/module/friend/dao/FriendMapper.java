package com.game.gameserver.module.friend.dao;

import com.game.gameserver.module.friend.entity.FriendEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/8 12:04
 */
@Mapper
@Repository
public interface FriendMapper {
    List<FriendEntity> selectFriendEntityList(long playerId);
    FriendEntity select(long id);
    int update(FriendEntity friendEntity);
    int insert(FriendEntity friendEntity);
    int delete(long id);
}
