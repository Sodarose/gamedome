package com.game.gameserver.module.player.helper;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.Message;
import com.game.protocol.TipProtocol;
import com.game.util.MessageUtil;

/**
 * @author xuewenkang
 * @date 2020/7/2 9:36
 */
public class PlayerHelper {

    public static void notifyPlayerPressMessage(Player player) {
        TipProtocol.TipMessage tipMessage = TipProtocol.TipMessage.newBuilder().setMsg("").build();
        Message message = MessageUtil.createMessage(ModuleKey.TIP_MODULE, (short) 0,
                tipMessage.toByteArray());
        player.getChannel().writeAndFlush(message);
    }
}
