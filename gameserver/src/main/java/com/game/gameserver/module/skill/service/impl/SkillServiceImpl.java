package com.game.gameserver.module.skill.service.impl;

import com.game.gameserver.common.Result;
import com.game.gameserver.module.skill.dao.SkillMapper;
import com.game.gameserver.module.skill.entity.Skill;
import com.game.gameserver.module.skill.model.PlayerSkill;
import com.game.gameserver.module.skill.service.SkillService;
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
    private SkillMapper skillMapper;



    /**
     * 读取用户技能
     *
     * @param playerId
     * @return com.game.gameserver.module.skill.model.SkillBar
     */
    @Override
    public PlayerSkill loadPlayerSkill(Long playerId) {
        return null;
    }

    /**
     * 学习技能
     *
     * @param playerId 玩家Id
     * @param skillId  所学技能Id
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Result learnSkill(Long playerId, int skillId) {
        return null;
    }

    /**
     * 放弃技能
     *
     * @param playerId 玩家Id
     * @param id
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Result giveUpSkill(Long playerId, int id) {
        return null;
    }

    /**
     * 得到技能列表
     *
     * @param playerId
     * @return java.util.List<com.game.gameserver.module.skill.entity.Skill>
     */
    @Override
    public List<Skill> getSkillList(int playerId) {
        return null;
    }

    /**
     * 更改技能在快捷技能栏中的位置
     *
     * @param playerId
     * @param id
     * @param bagIndex
     * @return com.game.gameserver.common.Result
     */
    @Override
    public Result changeSkillBagIndex(int playerId, int id, int bagIndex) {
        return null;
    }
}
