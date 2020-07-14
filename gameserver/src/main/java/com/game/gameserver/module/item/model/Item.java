package com.game.gameserver.module.item.model;

import lombok.Data;

/**
 * 道具
 *
 * @author xuewenkang
 * @date 2020/7/12 2:26
 */
@Data
public class Item  {
    /** 静态资源Id */
    private Integer itemConfigId;
    /** 数量 */
    private Integer num;
    /** 物品位置 */
    private Integer bagIndex;
    /** 装备耐久读 */
    private Integer durability;

    public Item(){

    }

    public void decreaseNum(int value){
        this.num -= value;
    }

    public void addNum(int value){
        this.num+=value;
    }
}
