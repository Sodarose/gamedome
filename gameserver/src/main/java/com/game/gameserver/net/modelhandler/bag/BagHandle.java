package com.game.gameserver.net.modelhandler.bag;

import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.bag.facade.BagFacade;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.BagProtocol;
import com.game.protocol.Message;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 背包Handle
 *
 * @author xuewenkang
 * @date 2020/5/26 21:58
 */
@ModuleHandler(module = ModuleKey.BAG_MODEL)
@Component
public class BagHandle extends BaseHandler {

    @Autowired
    private BagFacade bagFacade;

    /**
     * 打开背包
     *
     * @param message 消息 {roleId,bagId}
     * @param channel channel
     * @return void
     */
    @CmdHandler(cmd = BagCmd.OPEN_BAG)
    public void openBag(Message message, Channel channel) {
        try {
            BagProtocol.OpenBagReq openBagReq = BagProtocol.OpenBagReq.parseFrom(message.getData());
            BagProtocol.BagInfo bagInfo = bagFacade.openBag(openBagReq.getRoleId(),openBagReq.getBagId());
            if(bagInfo==null){
                return;
            }
            BagProtocol.OpenBagRes.Builder resBuilder = BagProtocol.OpenBagRes.newBuilder();
            resBuilder.setBagInfo(bagInfo);
            Message res = MessageUtil.createMessage(ModuleKey.BAG_MODEL,BagCmd.OPEN_BAG,resBuilder.build()
                    .toByteArray());
            channel.writeAndFlush(res);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭背包
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = BagCmd.CLOSE_BAG)
    public void closeBag(Message message, Channel channel) {

    }
}
