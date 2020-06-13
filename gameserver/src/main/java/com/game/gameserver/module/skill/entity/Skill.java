package com.game.gameserver.module.skill.entity;

import lombok.Data;

/**
 * 技能实体
 *
 * @author xuewenkang
 * @date 2020/6/10 10:31
 */
@Data
public class Skill {
    /** id */
    private Integer id;
    /** 技能Id */
    private Integer skillId;
    /** 在快捷技能栏的位置 null则为不在快捷技能栏中 */
    private Integer bagIndex;
    /** 技能所属玩家 */
    private Integer playerId;
}
