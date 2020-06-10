package com.game.gameserver.module.player.service.impl;

import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.protocol.PlayerProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/6/9 20:01
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerManager playerManager;


    /**
     * 登录用户角色
     *
     * @param playerId
     * @return com.game.protocol.PlayerProtocol.LoginRes
     */
    @Override
    public PlayerProtocol.LoginRes loginPlayer(int playerId) {
        return null;
    }

    /**
     * 根据账户获得该账户的角色列表
     *
     * @param account
     * @return com.game.protocol.PlayerProtocol.PlayerList
     */
    @Override
    public PlayerProtocol.PlayerList getPlayerList(int account) {
        return null;
    }

    /**
     * 根据Id 获取角色
     *
     * @param playerId
     * @return com.game.gameserver.module.player.model.PlayerObject
     */
    @Override
    public PlayerObject getPlayerObject(int playerId) {
        return playerManager.getPlayerObject(playerId);
    }
}
