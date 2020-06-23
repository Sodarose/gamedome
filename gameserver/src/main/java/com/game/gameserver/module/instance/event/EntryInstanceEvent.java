package com.game.gameserver.module.instance.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.event.EventType;

import java.util.ArrayList;
import java.util.List;

/**
 * 进入副本事件
 *
 * @author xuewenkang
 * @date 2020/6/22 13:18
 */
public class EntryInstanceEvent implements Event {

    /** 进入副本的Id名单 */
    private final List<Long> playerIds;

    public EntryInstanceEvent(List<Long> playerIds){
        this.playerIds = playerIds;
    }


    @Override
    public EventType getEventType() {
        return EventType.ENTRY_INSTANCE;
    }

    public List<Long> getPlayerIds(){
        return playerIds;
    }
}
