package com.game.gameserver.net.modelhandler.item;

import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.ItemProtocol;
import com.game.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/5/27 14:37
 */
@ModuleHandler(module = ModuleKey.ITEM_MODEL)
@Component
public class ItemHandle extends BaseHandler {
    @Autowired
    private ItemService itemService;

    /**
     * 打开背包
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ItemCmd.OPEN_BAG)
    public void openBar(Message message, Channel channel) {
        PlayerObject playerObject = channel.attr(PlayerManager.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerObject == null) {
            channel.close();
            return;
        }
        itemService.openBag(playerObject);
    }

    @CmdHandler(cmd = ItemCmd.OPEN_EQUIP_BAR)
    public void openEquipBar(Message message, Channel channel) {
        PlayerObject playerObject = channel.attr(PlayerManager.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerObject == null) {
            channel.close();
            return;
        }
        itemService.openEquipBar(playerObject);
    }

    @CmdHandler(cmd = ItemCmd.TAKE_EQUIP)
    public void takeEquip(Message message, Channel channel) {
        PlayerObject playerObject = channel.attr(PlayerManager.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerObject == null) {
            channel.close();
            return;
        }
        try {
            ItemProtocol.TakeEquip takeEquipMsg = ItemProtocol.TakeEquip.parseFrom(message.getData());
            itemService.takeEquip(playerObject, takeEquipMsg.getEquipId(), takeEquipMsg.getBagIndex());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }

    @CmdHandler(cmd = ItemCmd.USE_ITEM)
    public void useItem(Message message, Channel channel) {
        PlayerObject playerObject = channel.attr(PlayerManager.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerObject == null) {
            channel.close();
            return;
        }
        try {
            ItemProtocol.UserItem userItemMsg = ItemProtocol.UserItem.parseFrom(message.getData());
            itemService.useItem(playerObject, userItemMsg.getItemId(), userItemMsg.getBagIndex());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = ItemCmd.ADD_ITEM)
    public void addItem(Message message,Channel channel){

    }
}
