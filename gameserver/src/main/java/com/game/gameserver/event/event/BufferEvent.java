package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/16 17:10
 */
@Data
public class BufferEvent implements Event {
    private Player player;
    private Buffer buffer;

    public BufferEvent(Player player, Buffer buffer) {
        this.player = player;
        this.buffer = buffer;
    }
}
