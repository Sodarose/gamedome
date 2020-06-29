package com.game.gameserver.module.skill.dao;

import com.game.gameserver.module.skill.entity.Skill;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/11 20:13
 */
@Repository
@Mapper
public interface SkillMapper {
    /**
     * 得到玩家技能列表
     *
     * @param playerId 用户ID
     * @return java.util.List<com.game.gameserver.module.skill.entity.Skill>
     */
    List<Skill> getSkillList(long playerId);
}
