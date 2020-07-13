package com.game.gameserver.net.modelhandler.store;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.store.service.StoreService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.Message;
import com.game.protocol.Store;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/10 18:11
 */
@ModuleHandler(module = ModuleKey.STORE_MODULE)
@Component
public class StoreHandle extends BaseHandler {

    @Autowired
    private StoreService storeService;

    /**
     * 获取商品列表
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = StoreCmd.LIST)
    public void getCommodityList(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        Store.CommodityList commodityList = storeService.getCommodityList();
        Message res = MessageUtil.createMessage(ModuleKey.STORE_MODULE, StoreCmd.LIST, commodityList.toByteArray());
        channel.writeAndFlush(res);
    }

    /**
     * 购买物品
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = StoreCmd.BUY)
    public void buyCommodity(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            Store.BuyCommodityReq req = Store.BuyCommodityReq.parseFrom(message.getData());
            Store.BuyCommodityRes buyCommodityRes = storeService.bugCommodity(player,
                    req.getCommodityId(), req.getNum());
            Message msg = MessageUtil.createMessage(ModuleKey.STORE_MODULE, StoreCmd.BUY, buyCommodityRes.toByteArray());
            channel.writeAndFlush(msg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 出售物品
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = StoreCmd.SELL)
    public void sellCommodity(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            Store.SellGoodsReq sellGoodsReq = Store.SellGoodsReq.parseFrom(message.getData());
            Store.SellGoodsRes sellGoodsRes = storeService.sellCommodity(player, sellGoodsReq.getBagIndex(), sellGoodsReq.getGoodsId(),
                    sellGoodsReq.getNum());
            Message msg = MessageUtil.createMessage(ModuleKey.STORE_MODULE, StoreCmd.SELL, sellGoodsRes.toByteArray());
            channel.writeAndFlush(msg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

}
