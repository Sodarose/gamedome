package com.game.gameserver.module.player.dao;

import com.game.gameserver.module.player.entity.Player;
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
    /**
     * 根据账户获取角色数据
     *
     * @param accountId
     * @return java.util.List<com.game.gameserver.module.player.entity.Player>
     */
    List<Role> queryRoleList(int accountId);

    /**
     * 根据角色Id 获取角色
     *
     * @param playerId
     * @return java.util.List<com.game.gameserver.module.player.entity.Player>
     */
    Player queryPlayer(Long playerId);

}
