package com.game.gameserver.module.player.service;

import com.game.gameserver.module.player.model.PlayerObject;

/**
 * @author xuewenkang
 * @date 2020/6/9 20:00
 */
public interface PlayerService {
    /**
     * 根据Id 获取角色
     *
     * @param playerId
     * @return com.game.gameserver.module.player.model.PlayerObject
     */
    PlayerObject getPlayerObject(int playerId);

}
