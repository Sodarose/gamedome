package com.game.module.bag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuewenkang
 * @date 2020/5/27 15:33
 */
@Data
public class Cell {
    private Integer id;
    private String itemName;
    private Integer itemType;
    private Integer itemCount;
    private Integer bagIndex;

    public Cell() {

    }

    public Cell(Integer bagIndex){
        this.bagIndex = bagIndex;
    }

    public Cell(Integer id, String itemName, Integer itemType, Integer itemCount, Integer bagIndex) {
        this.id = id;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemCount = itemCount;
        this.bagIndex = bagIndex;
    }

}
