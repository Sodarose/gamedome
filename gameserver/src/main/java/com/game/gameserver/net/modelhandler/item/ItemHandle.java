
package com.game.gameserver.net.modelhandler.item;

import com.game.gameserver.module.item.service.ItemService;
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
 * @date 2020/5/27 14:37
 */

@ModuleHandler(module = ModuleKey.ITEM_MODULE)
@Component
public class ItemHandle extends BaseHandler {

    @Autowired
    private ItemService itemService;

    @CmdHandler(cmd = ItemCmd.SHOW_ITEM)
    public void showItem(Message message, Channel channel){
        Player userTask = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (userTask == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        int bagType = Integer.parseInt(param[0]);
        int bagIndex = Integer.parseInt(param[1]);
        itemService.showItem(userTask,bagType,bagIndex);
    }

    @CmdHandler(cmd = ItemCmd.USE_ITEM)
    public void useItem(Message message,Channel channel){
        Player userTask = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (userTask == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int bagIndex = Integer.parseInt(message.getContent());
        itemService.useItem(userTask,bagIndex);
    }
}

