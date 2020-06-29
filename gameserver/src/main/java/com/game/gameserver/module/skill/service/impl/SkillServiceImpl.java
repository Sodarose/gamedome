package com.game.gameserver.module.skill.service.impl;

import com.game.gameserver.module.skill.dao.SkillMapper;
import com.game.gameserver.module.skill.entity.PlayerSkill;
import com.game.gameserver.module.skill.entity.Skill;
import com.game.gameserver.module.skill.manager.SkillManager;
import com.game.gameserver.module.skill.service.SkillService;
import com.game.protocol.SkillProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 技能服务
 *
 * @author xuewenkang
 * @date 2020/6/11 10:34
 */
@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillManager skillManager;

    /**
     * 获取当前职业的所有技能列表
     *
     * @param careerId
     * @return com.game.protocol.SkillProtocol.CareerSkillListRes
     */
    @Override
    public SkillProtocol.CareerSkillListRes getCareerSkillList(int careerId) {
        return null;
    }

    /**
     * 获取当前玩家的技能列表
     *
     * @param playerId
     * @return com.game.protocol.SkillProtocol.SkillListRes
     */
    @Override
    public SkillProtocol.SkillListRes getPlayerSkillList(long playerId) {
        return null;
    }

    /**
     * 学习技能
     *
     * @param playerId
     * @param skillId
     * @return com.game.protocol.SkillProtocol.LearnSkillRes
     */
    @Override
    public SkillProtocol.LearnSkillRes learnSkill(long playerId, int skillId) {
        return null;
    }

    /**
     * 遗忘技能
     *
     * @param playerId
     * @param skillId
     * @return com.game.protocol.SkillProtocol.RemoveSkillRes
     */
    @Override
    public SkillProtocol.RemoveSkillRes removeSkill(long playerId, int skillId) {
        return null;
    }
}
