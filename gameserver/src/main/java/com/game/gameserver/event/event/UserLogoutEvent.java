package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.user.module.User;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/21 15:16
 */
@Data
public class UserLogoutEvent implements Event {
    private User user;
    public UserLogoutEvent(User user){
        this.user = user;
    }
}
