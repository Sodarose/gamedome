package com.game.gameserver.module.skill.service;

import com.game.protocol.SkillProtocol;

/**
 * @author xuewenkang
 * @date 2020/6/11 10:34
 */
public interface SkillService {

    /**
     *
     * 获取当前职业的所有技能列表
     * @param careerId
     * @return com.game.protocol.SkillProtocol.CareerSkillListRes
     */
    SkillProtocol.CareerSkillListRes getCareerSkillList(int careerId);

    /**
     * 获取当前玩家的技能列表
     *
     * @param playerId
     * @return com.game.protocol.SkillProtocol.SkillListRes
     */
    SkillProtocol.SkillListRes getPlayerSkillList(long playerId);

    /**
     * 学习技能
     *
     * @param playerId
     * @param skillId
     * @return com.game.protocol.SkillProtocol.LearnSkillRes
     */
    SkillProtocol.LearnSkillRes learnSkill(long playerId,int skillId);

    /**
     * 遗忘技能
     *
     * @param playerId
     * @param skillId
     * @return com.game.protocol.SkillProtocol.RemoveSkillRes
     */
    SkillProtocol.RemoveSkillRes removeSkill(long playerId,int skillId);

}
