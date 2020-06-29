package com.game.gameserver.module.monster.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.event.EventType;
import lombok.Getter;

/**
 * 怪物死亡事件
 *
 * @author xuewenkang
 * @date 2020/6/21 14:33
 */
@Getter
public class MonsterDeadEvent implements Event {
    /** 死亡的怪物Id */
    private final Long monsterId;
    public MonsterDeadEvent(Long monsterId) {
        this.monsterId = monsterId;
    }

    @Override
    public EventType getEventType() {
        return EventType.KILL_MONSTER;
    }
}
