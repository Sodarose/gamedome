package com.game.gameserver.module.shop.service;

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
public class StoreService {
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
            NotificationHelper.notifyPlayer(player,"不存在该商店");
            return;
        }
        NotificationHelper.notifyPlayer(player,ShopHelper.buildShop(shop));
    }

    /**
     * 单买
     *
     * @param player
     * @param shopId
     * @param goodsId
     * @return void
     */
    public void singleBuy(Player player, int shopId, int goodsId) {
        Shop shop = shopManager.getShop(shopId);
        if (shop == null) {
            NotificationHelper.notifyPlayer(player,"不存在该商店");
            return;
        }
        Goods goods = shop.getGoodsMap().get(goodsId);
        if(goods==null){
            NotificationHelper.notifyPlayer(player,"不存在该商品");
            return;
        }
        // 金钱是否足够
        if(player.getGolds()<goods.getPrice()){
            NotificationHelper.notifyPlayer(player,"金钱不足");
            return;
        }
        // 生成物品
        Item item = itemService.createItem(goods.getItemId(),1);
        // 添加道具是否成功
        if(!backBagService.addItem(player,item)){
            NotificationHelper.notifyPlayer(player,"购买失败");
        }
        // 扣除金币
        player.decrease(goods.getPrice());
        NotificationHelper.notifyPlayer(player,"购买成功");
        NotificationHelper.syncBackBag(player);
    }

    /**
     * 批量购买
     *
     * @param player
     * @param shopId
     * @param goodsId
     * @param num
     * @return void
     */
    public void bulkBuy(Player player, int shopId, int goodsId, int num) {

    }

    /**
     * 出售道具
     *
     * @param player
     * @param bagIndex
     * @param num
     * @return void
     */
    public void shell(Player player, int bagIndex, int num) {

    }

    /**
     * @param player
     * @param shopId
     * @param goodsId
     * @param num
     * @return void
     */
    private void buy(Player player, int shopId, int goodsId, int num) {

    }
}
