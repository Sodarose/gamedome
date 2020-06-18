package com.game.module.store;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/17 9:53
 */
@Data
public class Commodity {
    private int id;
    private int goodsType;
    private int goodsId;
    private String goodsName;
    private int storeType;
    private int originalPrice;
    private int price;
    private int limitCount;
}
