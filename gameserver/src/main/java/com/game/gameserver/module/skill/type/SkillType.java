package com.game.gameserver.module.skill.type;

/**
 * 技能类型
 *
 * @author xuewenkang
 * @date 2020/6/23 10:21
 */
public enum  SkillType {
    BUFFER(0,"自身BUFFER技能"),
    DAMAGE(1,"伤害型技能"),
    TREATMENT(2,"辅助型技能"),
    SUMMONING(3,"召唤型技能")
    ;
    private int type;
    private String desc;

    SkillType(int type,String desc){
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
