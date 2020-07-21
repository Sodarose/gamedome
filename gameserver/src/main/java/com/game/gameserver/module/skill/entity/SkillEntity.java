package com.game.gameserver.module.skill.entity;

import lombok.Data;

/**
 * 技能实体
 *
 * @author xuewenkang
 * @date 2020/6/10 10:31
 */
@Data
public class SkillEntity {
    /** 技能Id */
    private Integer skillId;
    /** 技能所属玩家 */
    private Long playerId;
    /** 技能当前等级 */
    private Integer level;
}
