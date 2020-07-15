package com.game.gameserver.net.modelhandler.auction;

import com.game.gameserver.module.auction.service.AuctionService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/7/13 6:09
 */
@ModuleHandler(module = ModuleKey.AUCTION_MODULE)
@Component
public class AuctionHandle extends BaseHandler {

    @Autowired
    private AuctionService auctionService;

    @CmdHandler(cmd = AuctionCmd.SHOW_AUCTION_HOUSE)
    public void showAuctionHouse(Message message, Channel channel){
        Player userTask = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (userTask == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        auctionService.showAuctionHouse(userTask);
    }

    @CmdHandler(cmd = AuctionCmd.SHOW_ME_AUCTION)
    public void showMeAuction(Message message, Channel channel){
        Player userTask = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (userTask == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        auctionService.showPlayerAuctionItem(userTask);
    }

    @CmdHandler(cmd = AuctionCmd.PUSH_AUCTION)
    public void pushAuction(Message message,Channel channel){
        Player userTask = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (userTask == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        int model = Integer.parseInt(param[0]);
        int bagIndex = Integer.parseInt(param[1]);
        int num = Integer.parseInt(param[2]);
        int price = Integer.parseInt(param[3]);
        auctionService.publishItem(userTask,model,bagIndex,num,price);
    }

    @CmdHandler(cmd = AuctionCmd.TAKE_AUCTION)
    public void takeAuction(Message message,Channel channel){
        Player userTask = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (userTask == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        long auctionItemId = Long.parseLong(message.getContent());
        auctionService.takeItem(userTask,auctionItemId);
    }

    @CmdHandler(cmd = AuctionCmd.AUCTION)
    public void auction(Message message,Channel channel){
        Player userTask = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (userTask == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        long auctionItemId = Long.parseLong(param[0]);
        int price = Integer.parseInt(param[1]);
        auctionService.buyAuctionItemByAuction(userTask,auctionItemId,price);
    }

    @CmdHandler(cmd = AuctionCmd.FIXED_PRICE)
    public void fixedPrice(Message message,Channel channel){
        Player userTask = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (userTask == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        long auctionItemId = Long.parseLong(message.getContent());
        auctionService.buyAuctionItemByFixedPrice(userTask,auctionItemId);
    }
}
