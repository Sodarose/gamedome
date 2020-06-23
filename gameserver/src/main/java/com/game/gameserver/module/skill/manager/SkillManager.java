package com.game.gameserver.module.skill.manager;

import com.game.gameserver.module.skill.model.PlayerSkill;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/17 16:48
 */
@Component
public class SkillManager {
    private final Map<Long, PlayerSkill> skillMap = new ConcurrentHashMap<>();

    public static SkillManager instance;

    public SkillManager(){
        instance = this;
    }

    public PlayerSkill getPlayerSkill(long playerId){
        return skillMap.get(playerId);
    }

}
