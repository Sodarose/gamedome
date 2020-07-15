package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.guild.model.Guild;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * 金币事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:47
 */
@Data
public class GoldsEvent implements Event {
    private Player player;

    public GoldsEvent(Player player){
        this.player = player;
    }
}
