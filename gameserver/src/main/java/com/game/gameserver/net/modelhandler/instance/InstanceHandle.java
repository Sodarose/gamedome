package com.game.gameserver.net.modelhandler.instance;

import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.Message;
import io.netty.channel.Channel;

/**
 * @author xuewenkang
 * @date 2020/6/8 19:21
 */
@ModuleHandler(module = ModuleKey.INSTANCE_MODULE)
public class InstanceHandle extends BaseHandler {

    /**
     * 得到副本列表
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = InstanceCmd.INSTANCE_LIST)
    public void getInstanceList(Message message, Channel channel){

    }

    /**
     * 进入副本
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = InstanceCmd.ENTRY_INSTANCE)
    public void entryInstance(Message message,Channel channel){

    }
}
