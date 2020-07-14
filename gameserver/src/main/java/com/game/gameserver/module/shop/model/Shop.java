package com.game.gameserver.module.shop.model;

import com.game.gameserver.module.item.model.Item;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 商店模型
 *
 * @author xuewenkang
 * @date 2020/7/14 9:51
 */
@Data
public class Shop {
    /** 商店Id */
    private int id;
    /** 商店名称 */
    private String name;
    /** 商店商品表 */
    private Map<Integer,Goods> goodsMap;

    public Shop(){

    }

    public Shop(int id,String name){
        this.id = id;
        this.name = name;
        this.goodsMap = new ConcurrentHashMap<>();
    }
}
