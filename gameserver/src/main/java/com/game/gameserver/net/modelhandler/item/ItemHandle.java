package com.game.gameserver.net.modelhandler.item;

import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.ItemProtocol;
import com.game.protocol.Message;
import com.game.util.MessageUtil;
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

    /**
     * 获取装备栏数据
     * */
    @CmdHandler(cmd = ItemCmd.EQUIP_BAG_REQ)
    public void handleEquipBagCmd(Message message, Channel channel){

    }

    /**
     * 获取背包数据
     * */
    @CmdHandler(cmd = ItemCmd.PLAYER_BAG_REQ)
    public void handlePlayerBagCmd(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        ItemProtocol.PlayerBag playerBag = itemService.getPlayerBag(player);
        Message res = MessageUtil.createMessage(ModuleKey.ITEM_MODULE,ItemCmd.PLAYER_BAG_REQ,playerBag.toByteArray());
        channel.writeAndFlush(res);
    }

    /**
     * 获取仓库数据
     * */
    @CmdHandler(cmd = ItemCmd.PLAYER_WAREHOUSE_REQ)
    public void handlePlayerWarehouseCmd(Message message, Channel channel){

    }

    /**
     * 使用道具
     * */
    @CmdHandler(cmd = ItemCmd.USE_ITEM)
    public void handleUserItemCmd(Message message, Channel channel){

    }

    /**
     * 丢弃道具
     * */
    @CmdHandler(cmd = ItemCmd.DROP_ITEM)
    public void handleDiscardItemCmd(Message message, Channel channel){

    }

    /**
     * 移动道具
     * */
    @CmdHandler(cmd = ItemCmd.MOVE_ITEM)
    public void handleMoveItemCmd(Message message, Channel channel){

    }

    /**
     * 获取装备栏数据
     * */
    @CmdHandler(cmd = ItemCmd.CLEAR_BAG)
    public void handleClearBagCmd(Message message, Channel channel){

    }
}
