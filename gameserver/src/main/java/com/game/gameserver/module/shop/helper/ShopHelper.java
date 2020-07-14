package com.game.gameserver.module.shop.helper;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.shop.model.Goods;
import com.game.gameserver.module.shop.model.Shop;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/14 9:59
 */
public class ShopHelper {

    public static String buildShopList(List<Shop> shopList) {
        StringBuilder sb = new StringBuilder("商店列表:").append("\n");
        shopList.forEach((shop) -> {
            sb.append("id:").append(shop.getId()).append("\n");
            sb.append("name:").append(shop.getName()).append("\n");
            sb.append("\n");
        });
        return sb.toString();
    }

    public static String buildShop(Shop shop) {
        StringBuilder sb = new StringBuilder();
        sb.append("id:").append(shop.getId()).append("\n");
        sb.append("name:").append(shop.getName()).append("\n");
        sb.append("goods:").append("\n");
        shop.getGoodsMap().forEach((key, value) -> {
            sb.append(buildGoods(value));
            sb.append("\n");
        });
        sb.append("\n");
        return sb.toString();
    }

    public static String buildGoods(Goods goods) {
        StringBuilder sb = new StringBuilder();
        ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(goods.getItemId());
        if (itemConfig == null) {
            return "";
        }
        sb.append("goodsId:").append(goods.getId()).append("\n");
        sb.append("goodsName:").append(itemConfig.getName()).append("\n");
        sb.append("price:").append(goods.getPrice()).append("\n");
        return sb.toString();
    }
}
