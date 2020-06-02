package com.game.module.item.entity;

import com.game.protocol.ItemProtocol;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/27 15:33
 */
@Data
public class Cell {
    /** 格子在背包中的位置*/
    private Integer bagIndex;
    private ItemProtocol.ItemInfo itemInfo;

    public Cell(){

    }

    public Cell(int bagIndex){
        this.bagIndex = bagIndex;
    }
}