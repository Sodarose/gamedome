package com.game.gameserver.net.modelhandler.fighter;

import com.game.gameserver.module.fighter.service.FighterService;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/7/20 5:51
 */
@Component
@ModuleHandler(module = ModuleKey.FIGHTER_MODEL)
public class FighterHandle extends BaseHandler {
    @Autowired
    private FighterService fighterService;

    @CmdHandler(cmd = FighterCmd.ATTACK)
    public void attack(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        String[] param = message.getContent().split("\\s+");
        long targetId = Long.parseLong(param[0]);
        int targetType = Integer.parseInt(param[1]);
        int skillId = Integer.parseInt(param[2]);
        fighterService.playerFight(player,targetId,targetType,skillId);
    }

    @CmdHandler(cmd = FighterCmd.CHANGE_MODEL)
    public void changeModel(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        int model = Integer.parseInt(message.getContent());
        fighterService.changeFighterMode(player,model);
    }
}
