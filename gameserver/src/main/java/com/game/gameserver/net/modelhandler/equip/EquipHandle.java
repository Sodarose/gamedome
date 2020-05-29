package com.game.gameserver.net.modelhandler.equip;

import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.equip.facade.EquipFacade;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.facade.PlayerFacade;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.util.TransFromUtil;
import com.game.protocol.EquipProtocol;
import com.game.protocol.Message;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 装备Handle
 * @author xuewenkang
 * @date 2020/5/26 21:16
 */
@ModuleHandler(module = ModuleKey.EQUIP_MODEL)
@Component
public class EquipHandle extends BaseHandler {
    private final static Logger logger = LoggerFactory.getLogger(EquipHandle.class);

    @Autowired
    private EquipFacade equipFacade;

    /**
     * 查看当前角色穿的某件装备
     * @param message 消息 {equipId}
     * @param channel channel
     * @return void
     */
    @CmdHandler(cmd = EquipCmd.CHECK_EQUIP)
    public void checkEquip(Message message, Channel channel){

    }

    /**
     * 卸下装备
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = EquipCmd.PUT_EQUIP)
    public void takeEquip(Message message,Channel channel){

    }

    /**
     * 穿上装备
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = EquipCmd.TAKE_EQUIP)
    public void putEquip(Message message,Channel channel){
        try {
            EquipProtocol.PutEquip putEquip = EquipProtocol.PutEquip.parseFrom(message.getData());
            Player player = channel.attr(PlayerFacade.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
            if(player==null){
                return;
            }


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }

    /**
     * 搜索装备
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = EquipCmd.SEARCH_EQUIP)
    public void searchEquip(Message message,Channel channel){

    }
}
