package com.game.gameserver.module.team.manager;

import com.game.gameserver.module.team.entity.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * 队伍列表
     */
    private final Map<Long, Team> teamObjectMap = new ConcurrentHashMap<>(16);

    public void putTeamObject(Team team) {
        teamObjectMap.put(team.getId(), team);
    }

    /**
     * 根据队伍Id，搜索队伍
     *
     * @param teamId
     * @return com.game.gameserver.module.team.model.TeamObject
     */
    public Team getTeamObject(long teamId) {
        return teamObjectMap.get(teamId);
    }

    /**
     * 删除一个队伍
     *
     * @param teamId
     * @return void
     */
    public void removeTeamObject(long teamId) {
        teamObjectMap.remove(teamId);
    }


    public List<Team> getListTeamObject() {
        List<Team> teamList = new ArrayList<>();
        for (Map.Entry<Long, Team> entry : teamObjectMap.entrySet()) {
            teamList.add(entry.getValue());
        }
        return teamList;
    }
}
