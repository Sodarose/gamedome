package com.game.gameserver.module.friend.dao;

import com.game.gameserver.module.friend.entity.PlayerFriendEntity;
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
    PlayerFriendEntity select(long playerId);
    int update(PlayerFriendEntity playerFriendEntity);
    int insert(PlayerFriendEntity playerFriendEntity);
    int delete(long playerId);
}
