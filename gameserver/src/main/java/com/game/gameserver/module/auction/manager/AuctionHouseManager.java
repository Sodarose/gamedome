package com.game.gameserver.module.auction.manager;

import com.game.gameserver.module.auction.dao.AuctionDbService;
import com.game.gameserver.module.auction.entity.AuctionItem;
import com.game.gameserver.module.auction.type.AuctionModel;
import com.game.gameserver.module.email.service.EmailService;
import com.game.gameserver.module.email.type.SystemSender;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
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

    /** 拍卖行线程 */
    private final static ThreadFactory AUCTION_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("AuctionHouse-%d").setUncaughtExceptionHandler((t,e) -> e.printStackTrace()).build();
    private final static ScheduledThreadPoolExecutor AUCTION_HOUSE_THREAD =
            new ScheduledThreadPoolExecutor(1,AUCTION_THREAD_FACTORY);

    @Autowired
    private AuctionDbService auctionDbService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private PlayerService playerService;

    public void loadAuctionItem() {
        logger.info("读取拍卖行信息");
        // 获取全部拍卖信息
        List<AuctionItem> auctionItemList = auctionDbService.selectAuctionItemList();
        // 过期拍卖品
        List<AuctionItem> expireAuctionItem = new ArrayList<>();
        // 遍历
        auctionItemList.forEach(auctionItem -> {
            if (isExpire(auctionItem)) {
                expireAuctionItem.add(auctionItem);
            } else {
                // 未过期 放入缓存
                LONG_AUCTION_ITEM_MAP.put(auctionItem.getId(), auctionItem);
            }
        });

        // 处理已经过期的拍卖品
        processExpireAuctionList(expireAuctionItem);
        // 执行拍卖品监听线程
        actionAuctionListener();
    }

    /**
     * 是否过期
     *
     * @param auctionItem
     * @return boolean
     */
    private boolean isExpire(AuctionItem auctionItem) {
        return System.currentTimeMillis() - auctionItem.getPublishTime() > EXPIRE_TIME;
    }

    /**
     * 处理过期的拍卖品列表
     *
     * @param expireAuctionItem
     * @return void
     */
    private void processExpireAuctionList(List<AuctionItem> expireAuctionItem) {
        expireAuctionItem.forEach(this::processExpireAuctionItem);
    }

    /**
     * 处理过期的拍卖品
     *
     * @param auctionItem
     * @return void
     */
    private void processExpireAuctionItem(AuctionItem auctionItem) {
        // 根据拍卖模式 进行不同的处理
        if(auctionItem.getModel() == AuctionModel.AUCTION){
            // 创建道具
            Item item = itemService.createItem(auctionItem.getItemConfigId(),auctionItem.getNum());
            // 创建附件列表
            List<Item> attachments = new ArrayList<>();
            // 发送邮件
            emailService.sendEmail(SystemSender.AUCTION.getId(),
                    auctionItem.getBidderId(),"拍卖行:成功竞拍商品",
                    "恭喜您成功竞拍商品",0,attachments);
            // 发送金币
            emailService.sendEmail(SystemSender.AUCTION.getId(),
                    auctionItem.getBidderId(),"拍卖行:商品已卖出",
                    "恭喜您成功卖出商品",auctionItem.getPrice(),null);
            // 在线通知用户
            Player bidder = playerService.getPlayer(auctionItem.getBidderId());
            if(bidder!=null){
                NotificationHelper.notifyPlayer(bidder,"您已经成功竞拍商品");
            }
            Player player = playerService.getPlayer(auctionItem.getPlayerId());
            if(bidder!=null){
                NotificationHelper.notifyPlayer(bidder,"您的商品已经被卖出");
            }
        }else {
            // 一口价模式 流拍 发回给用户
            // 创建道具
            Item item = itemService.createItem(auctionItem.getItemConfigId(), auctionItem.getNum());
            // 创建附件列表
            List<Item> attachments = new ArrayList<>();
            // 发送邮件
            emailService.sendEmail(SystemSender.AUCTION.getId(),
                    auctionItem.getPlayerId(), "拍卖行:商品流拍",
                    "商品流拍", 0, attachments);
        }
        auctionDbService.deleteAsync(auctionItem.getId());
    }

    /**
     * 一分钟执行一次
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
        // 迭代
        Iterator<Map.Entry<Long, AuctionItem>> iterator =
                LONG_AUCTION_ITEM_MAP.entrySet().iterator();
        while (iterator.hasNext()){
            expireAuctionItem.add(iterator.next().getValue());
            iterator.remove();
        }
        // 处理过期的拍卖品
        processExpireAuctionList(expireAuctionItem);
    }


    public void putAuctionItem(long auctionItemId,AuctionItem auctionItem) {
        LONG_AUCTION_ITEM_MAP.put(auctionItem.getId(), auctionItem);
    }

    public void removeAuctionItem(long auctionItemId) {
        LONG_AUCTION_ITEM_MAP.remove(auctionItemId);
    }

    public AuctionItem getAuctionItem(long auctionItemId){
        return LONG_AUCTION_ITEM_MAP.get(auctionItemId);
    }

    public String buildAuctionHouseMsg(){
        StringBuilder sb = new StringBuilder("拍卖行信息:");
        sb.append("id").append("\t").append("名称").append("\t").append("竞拍模式").append("\t")
                .append("价格").append("\t").append("数量").append("\n");
        LONG_AUCTION_ITEM_MAP.forEach((key, value) -> {
            sb.append(value.getId()).append("\t")
                    .append(value.getItemConfigId()).append("\t")
                    .append(value.getModel()== AuctionModel.AUCTION?"竞拍":"一口价").append("\t")
                    .append(value.getPrice()).append("\t")
                    .append(value.getNum()).append("\t")
                    .append("\n");
        });
        return sb.toString();
    }

    /**
     * 获得玩家上架的拍卖品
     *
     * @param playerId
     * @return java.util.List<com.game.gameserver.module.auction.entity.AuctionItem>
     */
    public List<AuctionItem> getAuctionItemByPlayerId(long playerId){
        List<AuctionItem> list = new ArrayList<>();
        LONG_AUCTION_ITEM_MAP.forEach((key, value) -> {
            if(value.getPlayerId().equals(playerId)){
                list.add(value);
            }
        });
        return list;
    }
}
