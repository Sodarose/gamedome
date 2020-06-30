package com.game.module.fighter;

/**
 * @author xuewenkang
 * @date 2020/6/4 9:30
 */
public interface FighterCmd {
    /** 攻击 */
    short ATTACK = 1001;
    /** 撤退 */
    short EXIT = 1002;
    /** 切换战斗模式 */
    short CHANGE_MODEL = 1003;
    /** 使用技能 */
    short USE_SKILL = 1004;
}
