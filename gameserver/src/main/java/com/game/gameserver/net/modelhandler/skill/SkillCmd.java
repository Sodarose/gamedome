package com.game.gameserver.net.modelhandler.skill;

/**
 * @author xuewenkang
 * @date 2020/6/4 20:34
 */
public interface SkillCmd {
    /** 职业技能列表 */
    short CAREER_SKILL_LIST = 1001;
    /** 玩家技能列表 */
    short PLAYER_SKILL_LIST = 1002;
    /** 学习技能*/
    short LEARN_SKILL = 1003;
    /** 遗忘技能*/
    short REMOVE_SKILL = 1004;
}
