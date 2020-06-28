package com.game.module.item;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.WordPage;
import com.game.module.order.CmdHandle;
import com.game.protocol.ItemProtocol;
import com.game.protocol.Message;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/1 16:33
 */
@Component
@ModuleHandler(module = ModuleKey.ITEM_MODULE)
public class ItemHandle extends BaseHandler {
    public Map<Integer, ItemProtocol.ItemInfo> playerBagMap = new HashMap<>();
    public Map<Integer, ItemProtocol> equipBagMap = new HashMap<>();

    @Autowired
    private ClientGameContext gameContext;
    @Autowired
    private WordPage wordPage;

    public void playerBagReq(){
        Message message = MessageUtil.createMessage(ModuleKey.ITEM_MODULE,ItemCmd.PLAYER_BAG_REQ,null);
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = ItemCmd.PLAYER_BAG_REQ)
    public void receivePlayerBagRes(Message message){
        try {
            ItemProtocol.PlayerBag playerBag = ItemProtocol.PlayerBag.parseFrom(message.getData());
            for(ItemProtocol.ItemInfo itemInfo:playerBag.getItemListList()){
                playerBagMap.put(itemInfo.getBagIndex(),itemInfo);
            }
            wordPage.clean();
            wordPage.printPlayerBag(playerBag);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public void equipBag(){

    }
}
