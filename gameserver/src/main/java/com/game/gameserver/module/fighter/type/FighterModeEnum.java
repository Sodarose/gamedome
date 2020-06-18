package com.game.gameserver.module.fighter.type;

/**
 * 战斗模式
 *
 * @author xuewenkang
 * @date 2020/6/18 21:49
 */
public enum  FighterModeEnum {
    /** 和平模式 不会被玩家攻击 */
    PEACE,
    /** 全体模式 可以攻击任何可攻击对象 */
    ALL,
    /** 组队模式 不攻击队友 */
    TEAM,
    /** 善恶     只能攻击红名玩家*/
    EVIL;
}
