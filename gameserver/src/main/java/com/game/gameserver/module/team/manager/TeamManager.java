package com.game.gameserver.module.team.manager;

import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.model.TeamObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组队Manager
 *
 * @author xuewenkang
 * @date 2020/6/9 16:40
 */
@Component
public class TeamManager {
    private final static Logger logger = LoggerFactory.getLogger(TeamManager.class);

    /** 队伍列表 */
    private final Map<Integer, TeamObject> teamObjectMap = new ConcurrentHashMap<>(16);

    /**
     * 创建一个队伍
     *
     * @param playerObject 创建者
     * @param teamName 队伍名
     * @return com.game.gameserver.module.team.model.TeamObject
     */
    public TeamObject createTeamObject(PlayerObject playerObject,String teamName,int maxCount){
        if(playerObject.getTeamId()!=null){
            logger.info("还用户已经在组队状态，禁止创建队伍");
            return null;
        }
        TeamObject teamObject = new TeamObject(playerObject,teamName,maxCount);
        teamObjectMap.put(teamObject.getId(),teamObject);
        return teamObject;
    }

    /**
     * 根据队伍Id，搜索队伍
     *
     * @param teamId
     * @return com.game.gameserver.module.team.model.TeamObject
     */
    public TeamObject getTeamObject(int teamId){
        return teamObjectMap.get(teamId);
    }

    /**
     * 删除一个队伍
     *
     * @param teamId
     * @return void
     */
    public void removeTeamObject(int teamId){
        teamObjectMap.remove(teamId);
    }

}
