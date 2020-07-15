package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * 退出事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:43
 */
@Data
public class LogoutEvent implements Event {
    private  Player player;

    public LogoutEvent(Player player){
        this.player = player;
    }
}
