package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * PK事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:47
 */
@Data
public class PKEvent implements Event {
    private Player initiator;
    private Player target;

}
