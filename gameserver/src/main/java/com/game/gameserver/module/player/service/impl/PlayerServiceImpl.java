package com.game.gameserver.module.player.service.impl;

import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.service.PlayerService;
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
