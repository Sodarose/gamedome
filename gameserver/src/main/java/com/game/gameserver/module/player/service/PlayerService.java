package com.game.gameserver.module.player.service;

import com.game.gameserver.module.player.model.PlayerObject;
import com.game.protocol.PlayerProtocol;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author xuewenkang
 * @date 2020/6/9 20:00
 */
public interface PlayerService {
    AttributeKey<PlayerObject> PLAYER_ENTITY_ATTRIBUTE_KEY = AttributeKey.newInstance("PLAYER_ENTITY_ATTRIBUTE_KEY");


    /**
     * 根据账户获得该账户的角色列表
     *
     * @param account
     * @return com.game.protocol.PlayerProtocol.PlayerList
     */
    PlayerProtocol.PlayerListRes getRoleList(int account);

    /**
     * 登录用户角色
     *
     * @param playerId
     * @param channel
     * @return com.game.protocol.PlayerProtocol.LoginRes
     */
    PlayerProtocol.LoginPlayerRes loginRole(Long playerId, Channel channel);

    /**
     * 获取角色信息
     *
     * @param playerObject
     * @return com.game.protocol.PlayerProtocol.PlayerInfo
     */
    PlayerProtocol.PlayerInfo getPlayerInfo(PlayerObject playerObject);
}
