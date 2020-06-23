package com.game.gameserver.module.instance.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.event.EventType;
import lombok.Getter;

import java.util.List;

/**
 * 副本回收事件
 *
 * @author xuewenkang
 * @date 2020/6/20 18:28
 */
@Getter
public class InstanceRecycleEvent implements Event {

    private final Long instanceId;

    public InstanceRecycleEvent(Long instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public EventType getEventType() {
        return EventType.INSTANCE_RECOVERY;
    }
}
