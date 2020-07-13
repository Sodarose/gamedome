package com.game.gameserver.module.auction.manager;

import com.game.gameserver.module.auction.dao.AuctionDbService;
import com.game.gameserver.module.auction.entity.AuctionItem;
import com.game.gameserver.module.auction.type.AuctionModel;
import com.game.gameserver.module.notification.NotificationManager;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.manager.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuewenkang
 * @date 2020/7/7 10:08
 */
@Component
public class AuctionHouseManager {
    private final static Logger logger = LoggerFactory.getLogger(AuctionHouseManager.class);

    /**
     * 拍卖品本地缓存
     */
    private final static Map<Long, AuctionItem> LONG_AUCTION_ITEM_MAP = new ConcurrentHashMap<>();
    /**
     * 超过24小时即过期
     */
    private final static long EXPIRE_TIME = TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS);
    private final static ScheduledThreadPoolExecutor AUCTION_HOUSE_THREAD =
            new ScheduledThreadPoolExecutor(1);


    @Autowired
    private AuctionDbService auctionDbService;
    @Autowired
    private PlayerManager playerManager;

    public void loadAuctionItem() {
        // 获取全部拍卖信息
        List<AuctionItem> auctionItemList = auctionDbService.selectAuctionItemList();
        // 过期拍卖品
        List<AuctionItem> expireAuctionItem = new ArrayList<>();
        auctionItemList.forEach(auctionItem -> {
            if (isExpire(auctionItem)) {
                expireAuctionItem.add(auctionItem);
            } else {
                LONG_AUCTION_ITEM_MAP.put(auctionItem.getId(), auctionItem);
            }
        });
        // 处理过期的拍卖品
        processExpireAuctionList(expireAuctionItem);
        actionAuctionListener();
    }


    private boolean isExpire(AuctionItem auctionItem) {
        return System.currentTimeMillis() - auctionItem.getPublishTime() > EXPIRE_TIME;
    }

    private void processExpireAuctionList(List<AuctionItem> expireAuctionItem) {
        expireAuctionItem.forEach(this::processExpireAuctionItem);
    }

    private void processExpireAuctionItem(AuctionItem auctionItem) {
        // 判断商品模式 竞拍商品
        String result = "";
        if (auctionItem.getModel() == AuctionModel.AUCTION) {
            // 发送邮件给竞拍者

            // 发送邮件给拍卖者
            result = "您有一个物品已成功被竞拍";
        } else { // 一口价商品
            // 发送邮件给拍卖者
            result = "拍卖失败，物品已经发送到您的邮件";
        }
        // 如果用户在线 通知玩家
        Player player = playerManager.getPlayer(auctionItem.getPlayerId());
        if (player != null) {
            NotificationManager.notifyPlayer(player, result);
        }
        // 删除拍卖品
        auctionDbService.removeAuctionItem(auctionItem.getId());
    }

    public void putAuctionItem(AuctionItem auctionItem) {
        LONG_AUCTION_ITEM_MAP.put(auctionItem.getId(), auctionItem);
    }

    public void removeAuctionItem(long auctionItem) {
        LONG_AUCTION_ITEM_MAP.remove(auctionItem);
    }

    /**
     * 开启过期监听
     *
     * @param
     * @return void
     */
    private void actionAuctionListener() {
        AUCTION_HOUSE_THREAD.scheduleAtFixedRate(
                this::actionProcessExpireAuctionItem,
                500,
                1,
                TimeUnit.MINUTES
        );
    }

    private void actionProcessExpireAuctionItem() {
        List<AuctionItem> expireAuctionItem = new ArrayList<>();
        LONG_AUCTION_ITEM_MAP.forEach((key, value) -> {
            if (isExpire(value)) {
                expireAuctionItem.add(value);
            }
        });
        expireAuctionItem.forEach(auctionItem -> {
            LONG_AUCTION_ITEM_MAP.remove(auctionItem.getId());
        });
        // 处理过期的拍卖品
        processExpireAuctionList(expireAuctionItem);
    }

    /**
     * 简单返回拍卖行信息
     *
     * @param
     * @return java.lang.String
     */
    public String auctionHouseMsg() {
        StringBuilder sb = new StringBuilder("拍卖行信息:");
        sb.append("id").append("\t").append("名称").append("\t").append("竞拍模式").append("\t")
                .append("价格").append("\t").append("数量").append("\n");
        LONG_AUCTION_ITEM_MAP.forEach((key, value) -> {
            sb.append(value.getId()).append("\t")
                    .append(value.getItemConfigId()).append("\t")
                    .append(value.getModel()==AuctionModel.AUCTION?"竞拍":"一口价").append("\t")
                    .append(value.getPrice()).append("\t")
                    .append(value.getNum()).append("\t")
                    .append("\n");
        });
        return sb.toString();
    }
}
