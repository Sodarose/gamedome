package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * 副本事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:46
 */
@Data
public class InstanceEvent implements Event {
    private Player player;
    private int instanceId;

    public InstanceEvent(Player player,int instanceId){
        this.player = player;
        this.instanceId = instanceId;
    }
}
