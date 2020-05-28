package com.game.module.bag.entity;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuewenkang
 * @date 2020/5/27 15:33
 */
@Data
public class Cell {
    /** 格子在背包中的位置*/
    private Integer bagIndex;
    /** 装载的道具 */
    private Integer itemId;
    /** 装载的道具 */
    private String itemName;
    /** 道具的数量 */
    private Integer count;

    public Cell(){

    }

    public Cell(int bagIndex){
        this.bagIndex = bagIndex;
    }
}
