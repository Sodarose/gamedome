package com.game.gameserver.module.goods.entity;

import com.game.gameserver.common.config.EquipConfig;
import com.game.gameserver.util.GenIdUtil;

/**
 * 玩家装备
 *
 * @author xuewenkang
 * @date 2020/6/10 10:27
 */
public class Equip extends Goods {

    /** 当前耐久度 */
    private int durability;

    public Equip(){

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
