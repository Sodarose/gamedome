package com.game.gameserver.module.shop.service;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.shop.helper.ShopHelper;
import com.game.gameserver.module.shop.manager.ShopManager;
import com.game.gameserver.module.shop.model.Goods;
import com.game.gameserver.module.shop.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商店服务类
 *
 * @author xuewenkang
 * @date 2020/6/10 17:13
 */
@Service
public class ShopService {
    @Autowired
    private ShopManager shopManager;
    @Autowired
    private ItemService itemService;
    @Autowired
    private BackBagService backBagService;

    /**
     * 展示商店列表
     *
     * @param player
     * @return void
     */
    public void showShopList(Player player) {
        List<Shop> shopList = shopManager.getAllShop();
        NotificationHelper.notifyPlayer(player, ShopHelper.buildShopList(shopList));
    }

    /**
     * 展示商店
     *
     * @param player
     * @param shopId
     * @return void
     */
    public void showShop(Player player, int shopId) {
        Shop shop = shopManager.getShop(shopId);
        if (shop == null) {
            NotificationHelper.notifyPlayer(player, "不存在该商店");
            return;
        }
        NotificationHelper.notifyPlayer(player, ShopHelper.buildShop(shop));
    }


    /**
     * 购买道具
     *
     * @param player
     * @param shopId
     * @param goodsId
     * @param num
     * @return void
     */
    public void buy(Player player, int shopId, int goodsId, int num) {
        Shop shop = shopManager.getShop(shopId);
        if (shop == null) {
            NotificationHelper.notifyPlayer(player, "不存在该商店");
            return;
        }
        if (!shop.getGoodsMap().containsKey(goodsId)) {
            NotificationHelper.notifyPlayer(player, "商店不存在该商品");
            return;
        }
        if(num<=0){
            return;
        }
        Goods goods = shop.getGoodsMap().get(goodsId);
        // 该商品是否允许批量购买
        if (goods.isBulkBuy()) {
            bulkBuy(player, goods, num);
        } else {
            singleBuy(player, goods);
        }
    }

    private void bulkBuy(Player player, Goods goods, int num) {
        // 计算金钱
        int price = goods.getPrice() * num;
        // 金钱是否足够
        if (player.getGolds() < price) {
            NotificationHelper.notifyPlayer(player, "金币不足");
            return;
        }
        // 生成道具
        Item item = itemService.createItem(goods.getItemId(), num);
        // 判断背包容量
        if (!backBagService.hasSpace(player, item)) {
            return;
        }
        // 扣除金钱
        player.goldsChange(-price);
        // 放入背包
        backBagService.addItem(player, item);
        NotificationHelper.notifyPlayer(player, "购买成功");
    }

    private void singleBuy(Player player, Goods goods) {
        int price = goods.getPrice();
        // 金钱是否足够
        if (player.getGolds() < price) {
            NotificationHelper.notifyPlayer(player, "金币不足");
            return;
        }
        // 生成道具
        Item item = itemService.createItem(goods.getItemId(), 1);
        // 判断背包容量
        if (!backBagService.hasSpace(player, item)) {
            return;
        }
        // 扣除金钱
        player.goldsChange(-price);
        // 放入背包
        backBagService.addItem(player, item);
        // 通知
        NotificationHelper.syncBackBag(player);
        NotificationHelper.notifyPlayer(player, "购买成功");
        NotificationHelper.syncPlayer(player);
    }

    /**
     * 出售道具
     *
     * @param player
     * @param bagIndex
     * @return void
     */
    public void sell(Player player, int bagIndex) {
        // 出售道具
        Item item = backBagService.getItem(player, bagIndex);
        if (item == null) {
            NotificationHelper.notifyPlayer(player, "不存在该道具");
            return;
        }
        // 计算金钱
        ItemConfig itemConfig = StaticConfigManager.getInstance()
                .getItemConfigMap().get(item.getItemConfigId());
        int price = itemConfig.getPrice() * item.getNum();
        // 移除道具
        item = backBagService.removeItem(player, bagIndex);
        if (item == null) {
            return;
        }
        // 增加金币
        player.goldsChange(price);
        // 通知
        NotificationHelper.syncBackBag(player);
        NotificationHelper.syncPlayer(player);
    }
}
