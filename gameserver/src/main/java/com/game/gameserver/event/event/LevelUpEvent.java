package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * 升级事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:46
 */
@Data
public class LevelUpEvent implements Event {
    private Player player;

    public LevelUpEvent(Player player) {
        this.player = player;
    }
}
