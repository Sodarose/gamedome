package com.game.gameserver.common.entity;

/**
 * 活物类型
 *
 * @author xuewenkang
 * @date 2020/7/16 10:35
 */
public enum CreatureType {
    /** 玩家 */
    PLAYER(1),
    /** 怪物 */
    MONSTER(2),
    /** NPC */
    NPC(3),
    /** 宠物 */
    PET(4);

    private int type;

    CreatureType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
