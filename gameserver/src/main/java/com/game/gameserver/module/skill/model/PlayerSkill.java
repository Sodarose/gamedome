package com.game.gameserver.module.skill.entity;

import com.game.gameserver.module.skill.entity.Skill;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 角色技能容器
 *
 * @author xuewenkang
 * @date 2020/6/10 10:31
 */
public class PlayerSkill {
    /** 玩家 */
    private final long playerId;
    /** 以技能资源id为键 */
    private final Map<Integer,Skill> skillMap;

    public PlayerSkill(long playerId){
        this.playerId = playerId;
        this.skillMap = new ConcurrentHashMap<>();
    }

    public void initialize(List<Skill> skills){
        if(skills==null||skills.size()==0){
            return;
        }
        for(Skill skill:skills){
            skillMap.put(skill.getSkillId(),skill);
        }
    }

    public boolean hasSkill(int skillId){
        return skillMap.containsKey(skillId);
    }

}
