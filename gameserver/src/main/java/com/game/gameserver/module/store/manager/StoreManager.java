package com.game.gameserver.module.store.manager;

import com.game.gameserver.common.config.CommodityConfig;
import com.game.gameserver.module.store.model.Commodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    /** 商品列表 */
    private final Map<Integer, Commodity> commodityMap = new ConcurrentHashMap<>(16);

    /** 加载商店 */
    public void loadStore(){
        
    }

    /**
     * 创建一个商品
     *
     * @param commodityConfig
     * @return com.game.gameserver.module.store.model.Commodity
     */
    private Commodity createCommodity(CommodityConfig commodityConfig){
        return null;
    }

    public void addCommodity(CommodityConfig commodityConfig){

    }

    /**
     * 移除一个商品
     * @param id 商品Id
     * @return void
     */
    public void removeCommodity(int id){

    }

}
