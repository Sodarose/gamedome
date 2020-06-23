package com.game.gameserver.module.item.entity;

import com.game.gameserver.common.config.EquipConfig;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.util.GameUUID;

/**
 * 玩家装备
 *
 * @author xuewenkang
 * @date 2020/6/10 10:27
 */
public class Equip extends Item {

    /** 当前耐久度 */
    private int durability;

    public Equip(){

    }

    public static Equip valueOf(EquipConfig equipConfig){
        Equip equip = new Equip();
        equip.setId(GameUUID.getInstance().generate());
        equip.setItemType(ItemType.EQUIP);
        equip.setItemId(equipConfig.getId());
        equip.setNum(1);
        equip.setBound(0);
        equip.setDurability(equipConfig.getMaxDurability());
        return equip;
    }

    /**
     * 增加耐久度
     *
     * @param num
     * @return void
     */
    public void addDurability(int num){
        durability+=num;
    }

    /**
     * 减少耐久度
     *
     * @param num
     * @return void
     */
    public void reduceDurability(int num){
        durability-=num;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

}
