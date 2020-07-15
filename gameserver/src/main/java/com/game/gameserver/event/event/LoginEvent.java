package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * 登录事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:42
 */
@Data
public class LoginEvent implements Event {
    private Player player;

    public LoginEvent(Player player){
        this.player = player;
    }
}
