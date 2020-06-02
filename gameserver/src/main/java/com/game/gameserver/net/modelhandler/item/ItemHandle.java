package com.game.gameserver.net.modelhandler.item;

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

    }


    /**
     * 使用道具
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = ItemCmd.USE_ITEM)
    public void useItem(Message message,Channel channel){

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
