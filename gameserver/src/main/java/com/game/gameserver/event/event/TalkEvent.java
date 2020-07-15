package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.npc.model.Npc;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.task.entity.TaskEntity;
import lombok.Data;

/**
 * npc交谈事件
 *
 * @author xuewenkang
 * @date 2020/7/15 10:35
 */
@Data
public class TalkEvent implements Event {
    private Player player;
    private Npc npc;

    public TalkEvent(Player player,Npc npc){
        this.player = player;
        this.npc = npc;
    }
}
