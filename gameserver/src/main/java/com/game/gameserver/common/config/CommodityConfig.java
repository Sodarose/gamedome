package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 商品静态数据类
 *
 * @author xuewenkang
 * @date 2020/6/10 16:57
 */
@Data
public class CommodityConfig {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "type")
    private int type;
    @JSONField(name = "goodsType")
    private int goodsType;
    @JSONField(name = "goodsId")
    private int goodsId;
    @JSONField(name = "shoreType")
    private int storeType;
    @JSONField(name = "originalPrice")
    private int originalPrice;
    @JSONField(name = "price")
    private int price;
    @JSONField(name = "limitCount")
    private int limitCount;
}
