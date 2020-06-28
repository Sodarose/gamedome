package com.game.gameserver.module.skill.type;

/**
 * 技能类型
 *
 * @author xuewenkang
 * @date 2020/6/23 10:21
 */
public interface SkillType {
    /** 伤害型技能 */
    int DAMAGE = 1;
    /** 辅助性技能 */
    int ASSIST = 2;
    /** 召唤性技能 */
    int SUMMONER = 3;


    /** 范围性技能 */
    int AREA = 1;
    /** 指定目标性技能*/
    int TARGET = 2;
    /** 无目标技能*/
    int NOT_TARGET = 3;

    String HP = "HP";
    String MP = "MP";
}
