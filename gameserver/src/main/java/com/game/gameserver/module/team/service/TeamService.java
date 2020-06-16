package com.game.gameserver.module.team.service;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.model.TeamObject;

/**
 *
 *
 * @author xuewenkang
 * @date 2020/6/9 18:54
 */
public interface TeamService {

    /**
     * 创建队伍
     *
     * @param player
     * @param teamName
     * @param maxCount
     * @return com.game.gameserver.module.team.model.TeamObject
     */
    TeamObject createTeam(PlayerObject player, String teamName, int maxCount);



}
