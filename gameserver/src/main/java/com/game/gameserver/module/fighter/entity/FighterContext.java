package com.game.gameserver.module.fighter.entity;

import com.game.gameserver.common.config.SkillConfig;
import lombok.Data;

/**
 * 战报
 *
 * @author xuewenkang
 * @date 2020/6/23 9:14
 */
@Data
public class FighterContext {
    /** 玩家 */
    private String playerName;
    /** 目标名字*/
    private String targetName;
    /** 技能类型 */
    private int skillType;
    /** 技能名称*/
    private String skillName;
    /** 造成的伤害or治疗值*/
    private int value;
}
