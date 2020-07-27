package com.game.gameserver.module.auction.service;

import com.game.gameserver.module.auction.dao.AuctionDbService;
import com.game.gameserver.module.auction.entity.AuctionItem;
import com.game.gameserver.module.auction.helper.AuctionHelper;
import com.game.gameserver.module.auction.manager.AuctionHouseManager;
import com.game.gameserver.module.auction.type.AuctionModel;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.email.service.EmailService;
import com.game.gameserver.module.email.type.SystemSender;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.util.GameUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author xuewenkang
 * @date 2020/7/10 10:24
 */
@Service
public class AuctionService {

    @Autowired
    private AuctionHouseManager auctionHouseManager;
    @Autowired
    private AuctionDbService auctionDbService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BackBagService backBagService;
    @Autowired
    private ItemService itemService;


    /**
     * 展示拍卖行
     *
     * @param player
     * @return void
     */
    public void showAuctionHouse(Player player) {
        NotificationHelper.notifyPlayer(player, auctionHouseManager.buildAuctionHouseMsg());
    }

    /**
     * 展示玩家上架的拍卖品
     *
     * @param player
     * @return void
     */
    public void showPlayerAuctionItem(Player player) {
        List<AuctionItem> auctionItems = auctionHouseManager
                .getAuctionItemByPlayerId(player.getPlayerEntity().getId());
        NotificationHelper.notifyPlayer(player, AuctionHelper.buildAuctionHouse(auctionItems));
    }

    /**
     * 上架拍卖品
     *
     * @param player   玩家
     * @param bagIndex 物品位置
     * @param num      上架数量
     * @return void
     */
    public void publishItem(Player player, int model, int bagIndex, int num, int price) {
        // 获取物品
        Item item = backBagService.getItem(player, bagIndex);
        if (item == null) {
            NotificationHelper.notifyPlayer(player, "该位置没有物品存在");
            return;
        }
        if (item.getNum() < num) {
            NotificationHelper.notifyPlayer(player, "拍卖数超过持有数");
            return;
        }
        // 移除背包物品
        item = backBagService.removeItem(player, bagIndex, num);
        if (item == null) {
            NotificationHelper.notifyPlayer(player, "上架失败");
            return;
        }
        // 创建拍卖品
        AuctionItem auctionItem = new AuctionItem(GameUUID.getInstance().generate(),
                model, item.getItemConfigId(), num, price, player.getPlayerEntity().getId());
        auctionItem.setPublishTime(System.currentTimeMillis());
        // 放入拍卖缓存
        auctionHouseManager.putAuctionItem(auctionItem.getId(), auctionItem);
        // 持久化
        auctionDbService.insertAsync(auctionItem);
        NotificationHelper.notifyPlayer(player, "上架商品成功");
        NotificationHelper.syncBackBag(player);
    }

    /**
     * 下架拍卖品
     *
     * @param player
     * @param auctionItemId
     * @return void
     */
    public void takeItem(Player player, long auctionItemId) {
        AuctionItem auctionItem = auctionHouseManager.getAuctionItem(auctionItemId);
        if (auctionItem == null) {
            NotificationHelper.notifyPlayer(player, "无该Id的商品");
            return;
        }
        if (!auctionItem.getPlayerId().equals(player.getPlayerEntity().getId())) {
            return;
        }
        // 如果是竞拍 不允许下架
        if (auctionItem.getModel() == AuctionModel.AUCTION) {
            NotificationHelper.notifyPlayer(player, "竞拍不允许下架");
            return;
        }
        // 防止下架时正好有人购买
        Lock lock = auctionItem.getWriteLock();
        lock.lock();
        try {
            auctionItem = auctionHouseManager.getAuctionItem(auctionItemId);
            if (auctionItem == null) {
                NotificationHelper.notifyPlayer(player, "下架失败");
                return;
            }
            // 移除该拍卖品
            auctionHouseManager.removeAuctionItem(auctionItemId);
            // 创建道具
            Item item = itemService.createItem(auctionItem.getItemConfigId(), auctionItem.getNum());
            List<Item> attchments = new ArrayList<>();
            attchments.add(item);
            // 邮件发送
            emailService.sendEmail(SystemSender.AUCTION.getId(), auctionItem.getPlayerId(),
                    "下架商品", "", 0, attchments);
            // 移除商品
            auctionHouseManager.removeAuctionItem(auctionItemId);
            auctionDbService.deleteAsync(auctionItemId);
        } finally {
            lock.unlock();
        }
        NotificationHelper.notifyPlayer(player, "物品成功下架");
    }

    /**
     * 一口价购买
     *
     * @param player
     * @param auctionItemId
     * @return void
     */
    public void buyAuctionItemByFixedPrice(Player player, long auctionItemId) {
        AuctionItem auctionItem = auctionHouseManager.getAuctionItem(auctionItemId);
        if (auctionItem == null) {
            NotificationHelper.notifyPlayer(player, "无该Id的商品");
            return;
        }
        // 判断商品模式
        if (auctionItem.getModel() != AuctionModel.FIXED_PRICE) {
            NotificationHelper.notifyPlayer(player, "不是一口价的商品");
            return;
        }
        // 金钱是否足够
        if (player.getGolds() < auctionItem.getPrice()) {
            NotificationHelper.notifyPlayer(player, "钱不够");
            return;
        }
        // 购买
        Lock lock = auctionItem.getWriteLock();
        lock.lock();
        try {
            auctionItem = auctionHouseManager.getAuctionItem(auctionItemId);
            if (auctionItem == null) {
                NotificationHelper.notifyPlayer(player, "购买失败");
                return;
            }
            // 购买商品
            player.goldsChange(-auctionItem.getPrice());
            // 邮件发送
            Item item = itemService.createItem(auctionItem.getItemConfigId(), auctionItem.getNum());
            List<Item> attachments = new ArrayList<>();
            attachments.add(item);
            emailService.sendEmail(SystemSender.AUCTION.getId(), player.getPlayerEntity().getId(), "成功拍卖物品",
                    "", 0, attachments);
            emailService.sendEmail(SystemSender.AUCTION.getId(), auctionItem.getPlayerId(), "您的商品已经被拍下",
                    "", auctionItem.getPrice(), null);
            // 移除商品
            auctionHouseManager.removeAuctionItem(auctionItemId);
            auctionDbService.deleteAsync(auctionItemId);
            NotificationHelper.notifyPlayer(player, "购买成功");
        } finally {
            lock.unlock();
        }
    }


    /**
     * 竞拍
     *
     * @param
     * @return
     */
    public void buyAuctionItemByAuction(Player player, long auctionItemId, int price) {
        AuctionItem auctionItem = auctionHouseManager.getAuctionItem(auctionItemId);
        if (auctionItem == null) {
            NotificationHelper.notifyPlayer(player, "无该Id的商品");
            return;
        }
        // 判断商品模式
        if (auctionItem.getModel() != AuctionModel.AUCTION) {
            NotificationHelper.notifyPlayer(player, "不是竞拍的商品");
            return;
        }
        // 金钱是否足够
        if (player.getGolds() < price) {
            NotificationHelper.notifyPlayer(player, "金币数目不符合");
            return;
        }
        if (player.getGolds() < auctionItem.getPrice()) {
            NotificationHelper.notifyPlayer(player, "不能竞拍比现在更低价");
            return;
        }
        Lock lock = auctionItem.getWriteLock();
        lock.lock();
        try {
            auctionItem = auctionHouseManager.getAuctionItem(auctionItemId);
            if (auctionItem == null) {
                NotificationHelper.notifyPlayer(player, "购买失败");
                return;
            }
            // 竞拍
            // 扣除金币
            player.goldsChange(-price);
            // 将上一任的金钱返还
            if (auctionItem.getBidderId() != null) {
                emailService.sendEmail(SystemSender.AUCTION.getId(), auctionItem.getBidderId(), "您竞拍的商品已有更高的价格",
                        "", auctionItem.getPrice(), null);
            }
            // 设置新的竞拍者
            auctionItem.setBidderId(player.getPlayerEntity().getId());
            auctionItem.setPrice(price);
            // 更新数据
            auctionDbService.updateAsync(auctionItem);
            NotificationHelper.notifyPlayer(player, "竞拍成功");
        } finally {
            lock.unlock();
        }
    }
}
