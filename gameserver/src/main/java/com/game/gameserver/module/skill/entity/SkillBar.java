package com.game.gameserver.module.skill.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 技能栏
 * @author xuewenkang
 * @date 2020/6/2 21:40
 */
public class SkillBarEntity {
    /** 技能栏最大栏数 **/
    private final static int SKILL_MAX = 13;
    /** 在技能栏中的技能 */
    private Skill[] skills = new Skill[SKILL_MAX];

    /** 角色所有技能 */
    private Map<Integer,Skill> skillMap = new HashMap<>();

    public void init(List<Skill> skillList){
        for(Skill skill:skillList){
            skillMap.put(skill.getSkillData().getId(),skill);
            if(skill.getSkillModel().getBagIndex()!=null){
                skills[skill.getSkillModel().getBagIndex()] = skill;
            }
        }
    }
}
