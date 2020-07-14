package com.game.gameserver.module.shop.model;

import lombok.Data;

/**
 * 商品
 *
 * @author xuewenkang
 * @date 2020/7/14 9:56
 */
@Data
public class Goods {
    /** 商品Id */
    private int id;
    /** 商品道具Id */
    private int itemId;
    /** 商品出售价格 */
    private int price;
    /** 是否允许批量购买*/
    private boolean bulkBuy;

    public Goods(){

    }

    public Goods(int id, int itemId, int price, boolean bulkBuy) {
        this.id = id;
        this.itemId = itemId;
        this.price = price;
        this.bulkBuy = bulkBuy;
    }
}
