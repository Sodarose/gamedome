/*
package com.game.gameserver.net.modelhandler.instance;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.InstanceProtocol;
import com.game.message.Message;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

*/
/**
 * @author xuewenkang
 * @date 2020/6/8 19:21
 *//*

@ModuleHandler(module = ModuleKey.INSTANCE_MODULE)
@Component
public class InstanceHandle extends BaseHandler {

    @Autowired
    private InstanceService instanceService;

    */
/**
     * 得到副本列表
     *
     * @param message
     * @param channel
     * @return void
     *//*

    @CmdHandler(cmd = InstanceCmd.INSTANCE_LIST)
    public void getInstanceList(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        InstanceProtocol.InstanceInfoListRes listRes = instanceService.getInstanceInfoList();
        Message resMsg = MessageUtil.createMessage(ModuleKey.INSTANCE_MODULE, InstanceCmd.INSTANCE_LIST
                , listRes.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    */
/**
     * 进入副本
     *
     * @param message
     * @param channel
     * @return void
     *//*

    @CmdHandler(cmd = InstanceCmd.ENTRY_INSTANCE)
    public void entryInstance(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            InstanceProtocol.EntryInstanceReq req = InstanceProtocol.EntryInstanceReq.parseFrom(message.getData());
            InstanceProtocol.EntryInstanceRes res = instanceService.entryInstanceInfo(player, req.getInstanceId(), req.getTeam());
            Message resMsg = MessageUtil.createMessage(ModuleKey.INSTANCE_MODULE, InstanceCmd.ENTRY_INSTANCE, res.toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = InstanceCmd.EXIT_INSTANCE)
    public void exitInstance(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        InstanceProtocol.ExitInstanceRes res = instanceService.exitInstance(player);
        Message resMsg = MessageUtil.createMessage(ModuleKey.INSTANCE_MODULE,InstanceCmd.EXIT_INSTANCE,
                res.toByteArray());
        channel.writeAndFlush(resMsg);
    }
}
*/
