package com.game.gameserver.net.modelhandler.fighter;

import com.game.gameserver.module.fighter.service.FighterService;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.FighterProtocol;
import com.game.protocol.Message;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/4 9:31
 */
@Component
@ModuleHandler(module = ModuleKey.FIGHTER_MODEL)
public class FighterHandle extends BaseHandler {
    @Autowired
    private FighterService fighterService;

    @CmdHandler(cmd = FighterCmd.ATTACK)
    public void handleAttackReq(Message message, Channel channel) {
        PlayerObject playerObject = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerObject == null) {
            return;
        }
        try {
            FighterProtocol.AttackReq attackReq = FighterProtocol.AttackReq.parseFrom(message.getData());
            FighterProtocol.AttackRes res = fighterService.playerAttackReq(playerObject.getPlayer().getId(),attackReq.getUnitId()
                    ,attackReq.getUnitType());
            Message resMsg = MessageUtil.createMessage(ModuleKey.FIGHTER_MODEL,FighterCmd.ATTACK,res.toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    
}
