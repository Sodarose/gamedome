package com.game.gameserver.module.player.dao;

import com.game.gameserver.module.player.model.Player;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/25 0:09
 */
@Repository
@Mapper
public interface PlayerMapper {
    /**
     * 根据AccountId 查找用户的角色
     * @param accountId 账户Id
     * @return java.util.List<com.game.gameserver.module.player.model.Player>
     */
    List<Player> findPlayerListByAccountId(Integer accountId);

    /**
     * 根据id返回角色信息
     * @param playerId 角色Id
     * @return com.game.gameserver.module.player.model.Role
     */
    Player findPlayerByPlayerId(Integer playerId);
}
