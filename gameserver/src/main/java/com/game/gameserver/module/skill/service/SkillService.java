package com.game.gameserver.module.skill.service;

import com.game.gameserver.common.Result;
import com.game.gameserver.module.skill.entity.Skill;
import com.game.gameserver.module.skill.model.PlayerSkill;
import com.game.gameserver.module.skill.vo.SkillVo;

import java.util.List;

/**
 *
 *
 * @author xuewenkang
 * @date 2020/6/11 10:34
 */
public interface SkillService {

    /**
     * 读取用户技能
     *
     * @param playerId
     * @return com.game.gameserver.module.skill.model.SkillBar
     */
    PlayerSkill loadPlayerSkill(int playerId);

    /**
     * 学习技能
     *
     * @param playerId 玩家Id
     * @param skillId 所学技能Id
     * @return com.game.gameserver.common.Result
     */
    Result learnSkill(int playerId,int skillId);

    /**
     * 放弃技能
     *
     * @param playerId 玩家Id
     * @param id
     * @return com.game.gameserver.common.Result
     */
    Result giveUpSkill(int playerId,int id);

    /**
     * 得到技能列表
     *
     * @param playerId
     * @return java.util.List<com.game.gameserver.module.skill.entity.Skill>
     */
    List<Skill> getSkillList(int playerId);


    /**
     * 更改技能在快捷技能栏中的位置
     *
     * @param playerId
     * @param id
     * @param bagIndex
     * @return com.game.gameserver.common.Result
     */
    Result changeSkillBagIndex(int playerId,int id,int bagIndex);
}
