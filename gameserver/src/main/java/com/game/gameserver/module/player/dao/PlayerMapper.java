package com.game.gameserver.module.player.dao;

import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.module.player.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * player dao层
 *
 * @author xuewenkang
 * @date 2020/6/10 10:20
 */
@Repository
@Mapper
public interface PlayerMapper {

    List<Long> selectRoleIds(long userId);

    List<Role> selectRoleList(long userId);

    PlayerEntity select(long playerId);

    int count(String name);

    int insert(PlayerEntity player);

    int update(PlayerEntity player);

    int delete(long playerId);
}
