package com.game.gameserver.module.skill.model;

import com.game.gameserver.common.entity.Creature;

/**
 * @author xuewenkang
 * @date 2020/7/17 14:39
 */
public interface SkillEffect {
    void runSkillEffect(Creature initiator,Skill skill);
    void runSkillEffect(Creature initiator,Creature target,Skill skill);
}
