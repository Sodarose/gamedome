package com.game.gameserver.module.team.service;

import com.game.gameserver.module.team.model.TeamObject;
import com.game.gameserver.module.team.vo.TeamVo;

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
     * @param playerId
     * @param teamName
     * @param maxCount
     * @return com.game.gameserver.module.team.model.TeamObject
     */
    TeamObject createTeam(int playerId, String teamName, int maxCount);

    /**
     * 得到队伍信息
     *
     * @param teamId
     * @return com.game.gameserver.module.team.model.TeamObject
     */
    TeamObject getTeamObject(int teamId);


}
