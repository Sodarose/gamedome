package com.game.gameserver.net.modelhandler.equipment;

import com.game.gameserver.module.equipment.service.EquipService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author xuewenkang
 * @date 2020/7/12 13:49
 */
@ModuleHandler(module = ModuleKey.EQUIP_MODULE)
@Component
public class EquipHandle extends BaseHandler {

    @Autowired
    private EquipService equipService;

    @CmdHandler(cmd = EquipCmd.SHOW_EQUIP_BAR)
    public void showEquip(Message message, Channel channel){

    }

    @CmdHandler(cmd = EquipCmd.PUT_EQUIP)
    public void putEquip(Message message,Channel channel){

    }

    @CmdHandler(cmd = EquipCmd.TAKE_EQUIP)
    public void takeEquip(Message message,Channel channel){

    }
}
