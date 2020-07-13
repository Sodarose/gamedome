package com.game.gameserver.module.team.manager;

import com.game.gameserver.module.team.model.Team;
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

    /** 本地组队缓存 */
    private final static Map<Long, Team> LOCAL_ITEM_MAP = new ConcurrentHashMap<>();

    public void putTeam(Team team){
        LOCAL_ITEM_MAP.put(team.getId(),team);
    }

    public Team getTeam(long itemId){
        return LOCAL_ITEM_MAP.get(itemId);
    }

    public void remove(long itemId){
        LOCAL_ITEM_MAP.remove(itemId);
    }

    public List<Team> getTeamList(){
        return new ArrayList<>(LOCAL_ITEM_MAP.values());
    }
}
