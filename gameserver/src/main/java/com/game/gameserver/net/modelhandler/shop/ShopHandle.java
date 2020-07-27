package com.game.gameserver.net.modelhandler.shop;

import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.shop.service.ShopService;
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
 * @date 2020/7/20 0:37
 */
@Component
@ModuleHandler(module = ModuleKey.SHOP_MODULE)
public class ShopHandle extends BaseHandler {

    @Autowired
    private ShopService shopService;

    @CmdHandler(cmd = ShopCmd.SHOW_SHOP_LIST)
    public void showShopList(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        shopService.showShopList(player);
    }

    @CmdHandler(cmd = ShopCmd.SHOW_SHOP)
    public void showShop(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        int shopId = Integer.parseInt(message.getContent());
        shopService.showShop(player,shopId);
    }

    @CmdHandler(cmd = ShopCmd.BUY)
    public void buy(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        int shopId = Integer.parseInt(param[0]);
        int goodsId = Integer.parseInt(param[1]);
        int num = Integer.parseInt(param[2]);
        shopService.buy(player,shopId,goodsId,num);
    }

    @CmdHandler(cmd = ShopCmd.SELL)
    public void sell(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        int bagIndex = Integer.parseInt(message.getContent());
        shopService.sell(player,bagIndex);
    }

}
