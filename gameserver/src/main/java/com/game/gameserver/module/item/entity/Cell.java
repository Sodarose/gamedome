package com.game.gameserver.module.item.entity;

import lombok.Data;

/**
 * 格子
 * @author xuewenkang
 * @date 2020/5/25 15:37
 */
@Data
public class Cell {
    private Integer bagIndex;
    private Item item;

    public Cell(){}

    public Cell(Integer bagIndex){
        this.bagIndex = bagIndex;
    }

    public Cell(Item item){
        bagIndex = item.getBagIndex();
        this.item = item;
    }
}
