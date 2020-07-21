package com.game.gameserver.module.skill.type;

/**
 * 技能范围类型
 *
 * @author xuewenkang
 * @date 2020/7/16 16:42
 */
public enum SkillRange {
    NONE(0,"不需要目标"),
    INDIVIDUAL(1,"单体"),
    AOE(2,"AOE"),
    ;
    private int type;
    private String desc;

    SkillRange(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
