package com.game.gameserver.common.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 商品配置
 *
 * @author xuewenkang
 * @date 2020/7/14 9:43
 */
@Data
public class GoodsConfig {
    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "itemId")
    private Integer itemId;
    @JSONField(name = "price")
    private Integer price;
    @JSONField(name = "bulkBuy")
    private Boolean bulkBuy;
}
