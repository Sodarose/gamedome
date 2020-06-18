package com.game.gameserver.module.team.service;

import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.entity.Team;

/**
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
    Team.CreateTeamRes createTeam(PlayerObject player, String teamName, int maxCount);

    /**
     * 解散队伍
     *
     * @param playerObject
     * @return
     */
    void dissolveTeam(PlayerObject playerObject);

    /**
     * 获取队伍列表
     *
     * @param
     * @return com.game.protocol.Team.TeamList
     */
    Team.TeamList getTeamList();

    /**
     * 进入队伍
     *
     * @param playerObject
     * @param teamId
     * @return com.game.protocol.Team.EntryTeamReq
     */
    void entryTeam(PlayerObject playerObject, Long teamId);

    /**
     * 退出队伍
     *
     * @param playerObject
     * @return void
     */
    void exitTeam(PlayerObject playerObject);

    /**
     * 提出队伍
     *
     * @param playerObject
     * @param playerId
     * @return void
     */
    void kickTeam(PlayerObject playerObject,Long playerId);
}
