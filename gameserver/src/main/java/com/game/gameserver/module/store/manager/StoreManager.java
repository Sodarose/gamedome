package com.game.gameserver.module.store.manager;

import com.game.gameserver.common.config.CommodityConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.store.entity.Commodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 商店管理类 管理商品
 *
 * @author xuewenkang
 * @date 2020/6/10 17:11
 */
@Component
public class StoreManager {
    private final static Logger logger = LoggerFactory.getLogger(StoreManager.class);

    /**
     * 商品列表
     */
    private final Map<Integer, Commodity> commodityMap = new ConcurrentHashMap<>(16);

    /**
     * 加载商店
     */
    public void loadStore() {
        logger.info("商店读取商品信息");
        Map<Integer, CommodityConfig> commodityConfigMap = StaticConfigManager.getInstance().getCommodityConfigMap();
        for (Map.Entry<Integer, CommodityConfig> entry : commodityConfigMap.entrySet()) {
            Commodity commodity = new Commodity(entry.getKey());
            commodityMap.put(entry.getKey(), commodity);
        }
    }

    public List<Commodity> commodityList() {
        List<Commodity> commodities = new ArrayList<>();
        for (Map.Entry<Integer, Commodity> entry : commodityMap.entrySet()) {
            commodities.add(entry.getValue());
        }
        return commodities;
    }

    public Commodity getCommodity(int commodityId){
        return commodityMap.get(commodityId);
    }
}
