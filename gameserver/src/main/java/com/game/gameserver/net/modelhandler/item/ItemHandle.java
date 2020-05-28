package com.game.gameserver.net.modelhandler.item;

import com.game.gameserver.dictionary.dict.DictItem;
import com.game.gameserver.module.item.facade.ItemFacade;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.ItemProtocol;
import com.game.protocol.Message;
import com.game.util.MessageUtil;
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
public class ItemHandle {
    @Autowired
    private ItemFacade itemFacade;

    /**
     * 搜索道具
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ItemCmd.SEARCH_ITEM)
    public void searchItem(Message message,Channel channel){

    }

    /**
     * 查看道具
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ItemCmd.CHECK_ITEM)
    public void checkItem(Message message, Channel channel){
        try {
            ItemProtocol.CheckItem checkItem = ItemProtocol.CheckItem.parseFrom(message.getData());
            DictItem item = itemFacade.checkItem(checkItem.getItemId());
            if(item==null){
                return;
            }
            ItemProtocol.DictItemInfo.Builder builder = ItemProtocol.DictItemInfo.newBuilder();
            builder.setName(item.getName());
            builder.setLevel(item.getLevel());
            builder.setQuality(item.getQuality());
            builder.setDescription(item.getDescription());
            Message res = MessageUtil.createMessage(ModuleKey.ITEM_MODEL,ItemCmd.CHECK_ITEM,
                    builder.build().toByteArray());
            channel.writeAndFlush(res);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }


    /**
     * 使用道具
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ItemCmd.USE_ITEM)
    public void useItem(Message message,Channel channel){
        try {
            ItemProtocol.UseItem useItem = ItemProtocol.UseItem.parseFrom(message.getData());

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 丢弃道具
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ItemCmd.DISCARD_ITEM)
    public void discardItem(Message message,Channel channel){

    }

    /**
     * 移动道具
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ItemCmd.MOVE_ITEM)
    public void moveItem(Message message,Channel channel){

    }

    /**
     * 添加道具
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ItemCmd.ADD_ITEM)
    public void addItem(Message message,Channel channel){

    }
}
