package com.game.gameserver.module.item.type;


/**
 * @author xuewenkang
 * @date 2020/7/12 3:00
 */
public enum  ItemType {
    /** 消耗品 */
    CONSUMABLES(1),
    /** 装备 */
    EQUIP(2);

    private int type;

    ItemType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
