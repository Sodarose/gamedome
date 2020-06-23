package com.game.gameserver.module.instance.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.event.EventType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 退出副本事件
 *
 * @author xuewenkang
 * @date 2020/6/22 13:18
 */
public class ExitInstanceEvent implements Event {
    /** 退出的Id名单 */
    private final List<Long> playerIds = new ArrayList<>();

    public ExitInstanceEvent(Long ...playerIds){
        this.playerIds.addAll(Arrays.asList(playerIds));
    }

    public ExitInstanceEvent(List<Long> playerIds){
        this.playerIds.addAll(playerIds);
    }

    @Override
    public EventType getEventType() {
        return EventType.EXIT_INSTANCE;
    }

    public List<Long> getPlayerIds(){
        return playerIds;
    }
}
