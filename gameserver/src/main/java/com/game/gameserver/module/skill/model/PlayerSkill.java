package com.game.gameserver.module.skill.model;

import com.game.gameserver.module.skill.entity.Skill;
import java.util.List;

/**
 * 角色技能
 *
 * @author xuewenkang
 * @date 2020/6/10 10:31
 */
public class PlayerSkill {
    /** 玩家技能列表 */
    private final List<Skill> skillList;

    public PlayerSkill(List<Skill> skillList){
        this.skillList = skillList;
    }

    public List<Skill> getSkillList(){
        return skillList;
    }

    public boolean add(Skill skill){
        return false;
    }

    public boolean remove(int id){
        return false;
    }

    public Skill getSkill(int id){
        return null;
    }

}
