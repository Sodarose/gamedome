package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.friend.entity.FriendEntity;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * 好友事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:46
 */
@Data
public class FriendEvent implements Event {
    private Player player;
    private Player friend;

    public FriendEvent(Player player,Player friend){
        this.player = player;
        this.friend = friend;
    }
}
