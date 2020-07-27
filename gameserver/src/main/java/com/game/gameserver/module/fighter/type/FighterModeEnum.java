package com.game.gameserver.module.fighter.type;

/**
 * 战斗模式
 *
 * @author xuewenkang
 * @date 2020/6/18 21:49
 */
public enum  FighterModeEnum {
    /** 和平模式 只攻击怪物 */
    PEACE(1,"和平模式"),
    /** 全体模式 可以攻击任何可攻击对象 */
    ALL(2,"全体模式"),
    /** 组队模式 不攻击队友 */
    TEAM(3,"团队模式"),
    /** 善恶     只能攻击红名玩家*/
    EVIL(4,"善恶模式");

    private int type;
    private String desc;

    FighterModeEnum(int type,String desc){
        this.type  = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
