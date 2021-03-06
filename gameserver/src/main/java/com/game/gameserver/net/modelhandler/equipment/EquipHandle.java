package com.game.gameserver.net.modelhandler.equipment;

import com.game.gameserver.module.equipment.service.EquipService;
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
 *
 * @author xuewenkang
 * @date 2020/7/12 13:49
 */
@ModuleHandler(module = ModuleKey.EQUIP_MODULE)
@Component
public class EquipHandle extends BaseHandler {

    @Autowired
    private EquipService equipService;

    @CmdHandler(cmd = EquipCmd.SHOW_EQUIP_BAR)
    public void showEquip(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        equipService.showEquipBar(player);
    }

    @CmdHandler(cmd = EquipCmd.PUT_EQUIP)
    public void putEquip(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int bagIndex = Integer.parseInt(message.getContent());
        equipService.putEquip(player,bagIndex);
    }

    @CmdHandler(cmd = EquipCmd.TAKE_EQUIP)
    public void takeEquip(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int part = Integer.parseInt(message.getContent());
        equipService.takeEquip(player,part);
    }
}
