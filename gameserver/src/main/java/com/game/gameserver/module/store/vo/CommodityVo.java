package com.game.gameserver.module.store.vo;

import lombok.Data;

/**
 * 商品VO类
 *
 * @author xuewenkang
 * @date 2020/6/10 17:38
 */
@Data
public class CommodityVo {
    private int id;
    private int type;
    private int goodsId;
    private String goodsName;
    private String price;
    private String originalPrice;
    private int limitCount;
}
