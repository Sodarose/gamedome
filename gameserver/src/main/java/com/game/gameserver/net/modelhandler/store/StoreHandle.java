package com.game.gameserver.net.modelhandler.store;

import com.game.gameserver.module.store.service.StoreService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xuewenkang
 * @date 2020/6/10 18:11
 */
@ModuleHandler(module = ModuleKey.STORE_MODULE)
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
    public void getCommodityList(Message message, Channel channel){

    }

    /**
     * 购买物品
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = StoreCmd.BUY)
    public void buyCommodity(Message message,Channel channel){

    }

    /**
     * 出售物品
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = StoreCmd.SELL)
    public void sellCommodity(Message message,Channel channel){

    }

}
