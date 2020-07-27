package com.game.gameserver.net.modelhandler.trade;

import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.trade.service.TradeService;
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
 * @date 2020/7/13 9:32
 */
@ModuleHandler(module = ModuleKey.TRADE_MODULE)
@Component
public class TradeHandle extends BaseHandler {

    @Autowired
    private TradeService tradeService;

    @CmdHandler(cmd = TradeCmd.SHOW_TRADE)
    public void showTrade(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        tradeService.showTradeBoard(player);
    }

    @CmdHandler(cmd = TradeCmd.INITIATE)
    public void initiate(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        long targetId = Long.parseLong(message.getContent());
        tradeService.initiateTrade(player,targetId);
    }

    @CmdHandler(cmd = TradeCmd.REPLY_TRADE)
    public void replyTrade(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        long tradeId = Long.parseLong(param[0]);
        int result = Integer.parseInt(param[1]);
        tradeService.replyTrade(player,tradeId,result);
    }

    @CmdHandler(cmd = TradeCmd.PUT_ITEM_TRADE)
    public void putItemTrade(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        int bagIndex = Integer.parseInt(message.getContent());
        tradeService.putItem2Trade(player,bagIndex);
    }

    @CmdHandler(cmd = TradeCmd.PUT_GOLD_TRADE)
    public void putGoldTrade(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        int price = Integer.parseInt(message.getContent());
        tradeService.putGolds2Trade(player,price);
    }

    @CmdHandler(cmd = TradeCmd.CANCEL_TRADE)
    public void cancelTrade(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        tradeService.cancelTrade(player);
    }

    @CmdHandler(cmd = TradeCmd.AFFIRM_TRADE)
    public void affirmTrade(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        tradeService.affirmTrade(player);
    }
}
