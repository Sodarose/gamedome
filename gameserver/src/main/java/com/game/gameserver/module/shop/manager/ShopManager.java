package com.game.gameserver.module.shop.manager;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.game.gameserver.common.config.GoodsConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.shop.model.Goods;
import com.game.gameserver.module.shop.model.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 商店管理器
 *
 * @author xuewenkang
 * @date 2020/7/14 9:50
 */
@Component
public class ShopManager {
    private final static Logger logger = LoggerFactory.getLogger(ShopManager.class);

    /** 商店缓存 */
    private final static Map<Integer, Shop> LOCAL_SHOP_MAP = new ConcurrentHashMap<>();

    /** 加载商店 */
    public void loadShop(){
        logger.info("加载商店配置");
        StaticConfigManager.getInstance().getShopConfigMap().forEach((key,value)->{
            Shop shop = new Shop(value.getId(),value.getName());
            // 导入商品
            List<Integer> goodsIdList = value.getGoods();
            goodsIdList.forEach(goodsId->{
                GoodsConfig goodsConfig = StaticConfigManager.getInstance().getGoodsConfigMap().get(goodsId);
                if(goodsConfig!=null){
                    Goods goods = new Goods(goodsConfig.getId(),goodsConfig.getItemId(),goodsConfig.getPrice()
                            ,goodsConfig.getBulkBuy());
                    // 放入商店
                    shop.getGoodsMap().put(goods.getId(),goods);
                }
            });
            // 放入缓存
            LOCAL_SHOP_MAP.put(shop.getId(),shop);
        });
    }

    public List<Shop> getAllShop(){
        return new ArrayList<>(LOCAL_SHOP_MAP.values());
    }

    public Shop getShop(int shopId){
        return LOCAL_SHOP_MAP.get(shopId);
    }

    public void putShop(int shopId,Shop shop){
        LOCAL_SHOP_MAP.put(shopId,shop);
    }

    public void remove(int shopId){
        LOCAL_SHOP_MAP.remove(shopId);
    }
}
