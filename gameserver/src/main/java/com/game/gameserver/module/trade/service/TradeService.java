package com.game.gameserver.module.trade.service;

import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.event.TradeEvent;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.trade.helper.TradeHelper;
import com.game.gameserver.module.trade.manager.TradeManager;
import com.game.gameserver.module.trade.model.TradeBar;
import com.game.gameserver.module.trade.model.TradeBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.concurrent.locks.Lock;

/**
 * @author xuewenkang
 * @date 2020/7/8 11:14
 */
@Service
public class TradeService {

    @Autowired
    private TradeManager tradeManager;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private BackBagService backBagService;

    public void showTradeBoard(Player player) {
        if (player.getCurrTrade() == null) {
            NotificationHelper.notifyPlayer(player, "你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(player.getCurrTrade());
        Lock lock = tradeBoard.getReadLock();
        lock.lock();
        try {
            NotificationHelper.notifyPlayer(player, TradeHelper.buildTradeBoard(tradeBoard));
        } finally {
            lock.unlock();
        }
    }


    /**
     * 发起交易
     *
     * @param player
     * @param targetId
     * @return void
     */
    public void initiateTrade(Player player, long targetId) {
        Player target = playerService.getPlayer(targetId);
        if (target == null) {
            NotificationHelper.notifyPlayer(player, "当前玩家不在线");
            return;
        }
        // 创建交易栏
        TradeBoard tradeBoard = new TradeBoard(player, target);
        tradeManager.putTradeBoard(tradeBoard.getId(), tradeBoard);
        // 发送邀请
        NotificationHelper.notifyPlayer(target, MessageFormat.format("玩家{0}请求交易,Id{1}",
                player.getPlayerEntity().getName(), tradeBoard.getId() + ""));
    }

    /**
     * 交易回复
     *
     * @param player
     * @param tradeId
     * @param result
     * @return void
     */
    public void replyTrade(Player player, long tradeId, int result) {
        TradeBoard tradeBoard = tradeManager.getTradeBoard(tradeId);
        if (tradeBoard == null) {
            return;
        }
        // 接受交易
        if (result == 0) {

            NotificationHelper.notifyPlayer(tradeBoard.getInitiator(), "玩家接受交易");
        } else {
            // 拒绝交易 移除该交易
            tradeManager.removeTradeBoard(tradeBoard.getId());
            tradeBoard.getAccepter().setCurrTrade(null);
            tradeBoard.getInitiator().setCurrTrade(null);
            NotificationHelper.notifyPlayer(tradeBoard.getInitiator(), "玩家拒绝交易");
        }
    }

    /**
     * 放入道具到交易栏中
     *
     * @param player
     * @param bagIndex
     * @return void
     */
    public void putItem2Trade(Player player, int bagIndex) {
        if (player.getCurrTrade() == null) {
            NotificationHelper.notifyPlayer(player, "你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(player.getCurrTrade());
        Lock lock = tradeBoard.getWriteLock();
        lock.lock();
        try {
            TradeBar tradeBar = tradeBoard.getTradeBarMap().get(player.getPlayerEntity().getId());
            Item item = backBagService.getItem(player, bagIndex);
            if (item == null) {
                return;
            }
            tradeBar.getItemList().add(item);
        } finally {
            lock.unlock();
        }
        NotificationHelper.notifyPlayer(player, "放入道具到交易栏中");
    }

    public void putGolds2Trade(Player player, int price) {
        if (player.getCurrTrade() == null) {
            NotificationHelper.notifyPlayer(player, "你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(player.getCurrTrade());
        Lock lock = tradeBoard.getWriteLock();
        lock.lock();
        try {
            if (price > player.getGolds()) {
                NotificationHelper.notifyPlayer(player, "金币大于自身持有金币");
                return;
            }
            tradeBoard.getTradeBarMap().get(player.getPlayerEntity().getId()).setGolds(price);
        } finally {
            lock.unlock();
        }
        NotificationHelper.notifyPlayer(player, "放入" + price + "金币");
    }

    /**
     * 取消交易
     *
     * @param player
     * @return void
     */
    public void cancelTrade(Player player) {
        if (player.getCurrTrade() == null) {
            NotificationHelper.notifyPlayer(player, "你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(player.getCurrTrade());
        Lock lock = tradeBoard.getWriteLock();
        lock.lock();
        try {
            tradeBoard.getInitiator().setCurrTrade(null);
            tradeBoard.getAccepter().setCurrTrade(null);
            NotificationHelper.notifyPlayer(tradeBoard.getInitiator(), "交易被取消");
            NotificationHelper.notifyPlayer(tradeBoard.getAccepter(), "交易被取消");
            // 移除缓存
            tradeManager.removeTradeBoard(tradeBoard.getId());
        } finally {
            lock.unlock();
        }
    }

    /**
     * 确认交易信息
     *
     * @param player
     * @return void
     */
    public void affirmTrade(Player player) {
        if (player.getCurrTrade() == null) {
            NotificationHelper.notifyPlayer(player, "你当前没有正在进行的交易");
            return;
        }
        TradeBoard tradeBoard = tradeManager.getTradeBoard(player.getCurrTrade());
        Lock lock = tradeBoard.getWriteLock();
        lock.lock();
        try {
            // 确认交易
            tradeBoard.getTradeBarMap().get(player.getPlayerEntity().getId()).setAffirm(true);
            Player accepter = tradeBoard.getAccepter();
            Player initiator = tradeBoard.getInitiator();
            TradeBar accepterBar = tradeBoard.getTradeBarMap().get(accepter.getPlayerEntity().getId());
            TradeBar initiatorBar = tradeBoard.getTradeBarMap().get(initiator.getPlayerEntity().getId());
            if (accepterBar.isAffirm() && initiatorBar.isAffirm()) {
                NotificationHelper.notifyPlayer(accepter, "正在交易");
                NotificationHelper.notifyPlayer(initiator, "正在交易");
                // 执行交易
                processTrade(tradeBoard);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 处理交易
     *
     * @param tradeBoard
     * @return void
     */
    private void processTrade(TradeBoard tradeBoard) {
        Player accepter = tradeBoard.getAccepter();
        Player initiator = tradeBoard.getInitiator();
        TradeBar accepterBar = tradeBoard.getTradeBarMap().get(accepter.getPlayerEntity().getId());
        TradeBar initiatorBar = tradeBoard.getTradeBarMap().get(initiator.getPlayerEntity().getId());
        // 双方交换道具与金币
        // 移除接受方道具和金币
        accepter.goldsChange(-accepterBar.getGolds());
        // 移除道具
        for (Item item : accepterBar.getItemList()) {
            backBagService.removeItem(accepter, item.getBagIndex());
        }

        // 移除发起方道具和金币
        initiator.goldsChange(-initiatorBar.getGolds());
        // 移除道具
        for (Item item : initiatorBar.getItemList()) {
            backBagService.removeItem(initiator, item.getBagIndex());
        }

        // 接受方增加道济和金币
        accepter.goldsChange(initiatorBar.getGolds());
        for (Item item : initiatorBar.getItemList()) {
            backBagService.addItem(accepter, item);
        }

        // 发起方增加道具和金币
        initiator.goldsChange(accepterBar.getGolds());
        for (Item item : accepterBar.getItemList()) {
            backBagService.addItem(initiator, item);
        }

        // 同步
        NotificationHelper.syncPlayer(initiator);
        NotificationHelper.syncPlayer(accepter);
        NotificationHelper.syncBackBag(initiator);
        NotificationHelper.syncBackBag(accepter);

        NotificationHelper.notifyPlayer(initiator, "交易成功");
        NotificationHelper.notifyPlayer(accepter, "交易成功");

        tradeBoard.getInitiator().setCurrTrade(null);
        tradeBoard.getAccepter().setCurrTrade(null);
        // 移除缓存
        tradeManager.removeTradeBoard(tradeBoard.getId());

        // 发出交易成功事件
        TradeEvent initiatorTradeEvent = new TradeEvent(tradeBoard.getInitiator());
        TradeEvent accepterTradeEvent = new TradeEvent(tradeBoard.getAccepter());
        EventBus.EVENT_BUS.fire(initiatorTradeEvent);
        EventBus.EVENT_BUS.fire(accepterTradeEvent);
    }
}
