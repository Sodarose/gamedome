package com.game.gameserver.module.shop.service;

import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.shop.manager.ShopManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 展示商店列表
     *
     * @param player
     * @return void
     */
    public void showShopList(Player player) {

    }

    /**
     * 展示商店
     *
     * @param player
     * @param shopId
     * @return void
     */
    public void showShop(Player player, int shopId) {

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
