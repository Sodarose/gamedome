package com.game.gameserver.module.player.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.event.EventType;

/**
 * 玩家掉线
 *
 * @author xuewenkang
 * @date 2020/6/17 14:17
 */
public class LostEvent implements Event {

    private final EventType type = EventType.LOST;

    private final Long playerId;

    public LostEvent(Long playerId) {
        this.playerId = playerId;
    }

    @Override
    public EventType getEventType() {
        return type;
    }

    public Long getPlayerId(){
        return playerId;
    }
}
