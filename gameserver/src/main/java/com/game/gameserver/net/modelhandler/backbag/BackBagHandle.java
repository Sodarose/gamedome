package com.game.gameserver.net.modelhandler.backbag;

import com.game.gameserver.module.backbag.service.BackBagService;
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
 * @date 2020/7/12 13:55
 */
@ModuleHandler(module = ModuleKey.BACK_BAG_MODULE)
@Component
public class BackBagHandle extends BaseHandler {

    @Autowired
    private BackBagService backBagService;

    @CmdHandler(cmd = BackBagCmd.SHOW_BACK_BAG)
    public void showBackBag(Message message, Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        backBagService.showPlayerBackBag(playerDomain);
    }

    @CmdHandler(cmd = BackBagCmd.MOVE_ITEM)
    public void moveItem(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        // 参数个数
        int paramNum = 2;
        if (param.length!= paramNum) {
            NotificationHelper.notifyChannel(channel, "参数错误");
            return;
        }
        int sourceIndex = Integer.parseInt(param[0]);
        int targetIndex = Integer.parseInt(param[1]);
        backBagService.moveItem(playerDomain,sourceIndex,targetIndex);
    }

    @CmdHandler(cmd = BackBagCmd.DISCARD_ITEM)
    public void discardItem(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        // 参数个数
        int paramNum = 2;
        if (param.length!= paramNum) {
            NotificationHelper.notifyChannel(channel, "参数错误");
            return;
        }
        int bagIndex = Integer.parseInt(param[0]);
        int num = Integer.parseInt(param[1]);
        backBagService.discardItem(playerDomain,bagIndex,num);
    }

    @CmdHandler(cmd = BackBagCmd.CLEAN_UP)
    public void clearUpBag(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        backBagService.clearUpBag(playerDomain);
    }

}
