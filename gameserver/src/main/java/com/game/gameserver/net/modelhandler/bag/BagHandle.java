package com.game.gameserver.net.modelhandler.bag;

import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.bag.facade.BagFacade;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.facade.PlayerFacade;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.util.TransFromUtil;
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
     * @param message 消息
     * @param channel channel
     * @return void
     */
    @CmdHandler(cmd = BagCmd.OPEN_BAG)
    public void openBag(Message message, Channel channel) {
        try {
            BagProtocol.OpenBag openBag = BagProtocol.OpenBag.parseFrom(message.getData());
            Player player = channel.attr(PlayerFacade.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
            if(player==null){
                return;
            }
            Bag bag = player.getBag();
            if(bag==null){
                return;
            }
            if(!bag.getId().equals(openBag.getBagId())){
                return;
            }
            BagProtocol.BagInfo bagInfo = TransFromUtil.bagTransFromBagProtocolBagInfo(bag);
            Message msg = MessageUtil.createMessage(ModuleKey.BAG_MODEL,BagCmd.OPEN_BAG,bagInfo.toByteArray());
            channel.writeAndFlush(msg);
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
