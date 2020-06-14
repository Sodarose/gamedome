package com.game.gameserver.module.team.service.impl;

import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.team.manager.TeamManager;
import com.game.gameserver.module.team.model.TeamObject;
import com.game.gameserver.module.team.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/6/9 18:54
 */
@Service
public class TeamServiceImpl implements TeamService {

    private final static Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Autowired
    private TeamManager teamManager;
    @Autowired
    private PlayerService playerService;

    /**
     * 创建队伍
     *
     * @param playerId
     * @param teamName
     * @param maxCount
     * @return com.game.gameserver.module.team.model.TeamObject
     */
    @Override
    public TeamObject createTeam(int playerId, String teamName, int maxCount) {
        PlayerObject playerObject = playerService.getPlayerObject(playerId);
        if(playerObject==null){
            logger.warn("不存的用户 id:{} 创建 队伍",playerId);
            return null;
        }
        if(playerObject.getTeamId()!=null){
            logger.info("该用户 id:{} 已经存在队伍，禁止再此创建队伍",playerObject.getUnitId());
            return null;
        }
        TeamObject teamObject = teamManager.createTeamObject(playerObject,teamName,maxCount);
        if(teamObject==null){
            logger.info("创建队伍失败");
            return null;
        }
        return teamObject;
    }

    /**
     * 得到队伍信息
     *
     * @param teamId 队伍ID
     * @return com.game.gameserver.module.team.model.TeamObject
     */
    @Override
    public TeamObject getTeamObject(int teamId) {
        return teamManager.getTeamObject(teamId);
    }




}
