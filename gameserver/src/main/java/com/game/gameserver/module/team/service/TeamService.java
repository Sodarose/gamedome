package com.game.gameserver.module.team.service;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.entity.Team;
import com.game.protocol.TeamProtocol;

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
    TeamProtocol.CreateTeamRes createTeam(PlayerObject player, String teamName, int maxCount);

    /**
     *
     * @param playerObject
     * @return com.game.protocol.TeamProtocol.CheckTeamRes
     */
    TeamProtocol.CheckTeamRes checkTeamRes(PlayerObject playerObject);

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
    TeamProtocol.TeamListRes getTeamList();

    /**
     * 进入队伍
     *
     * @param playerObject
     * @param teamId
     * @return com.game.protocol.Team.EntryTeamReq
     */
    TeamProtocol.EntryTeamRes entryTeam(PlayerObject playerObject, Long teamId);

    /**
     * 退出队伍
     *
     * @param playerObject
     * @return void
     */
    TeamProtocol.ExitTeamRes exitTeam(PlayerObject playerObject);

    /**
     * 提出队伍
     *
     * @param playerObject
     * @param playerId
     * @return void
     */
    void kickTeam(PlayerObject playerObject,Long playerId);
}
