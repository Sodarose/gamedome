package com.game.gameserver.module.store.model;

import com.game.gameserver.common.config.CommodityConfig;
import lombok.Data;

/**
 * 商品实体
 *
 * @author xuewenkang
 * @date 2020/6/10 17:13
 */
@Data
public class Commodity {
    /**
     * 商品静态数据Id
     */
    private final Integer commodityId;

    public Commodity(int commodityId) {
        this.commodityId = commodityId;
    }
}
