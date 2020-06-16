package com.game.gameserver.module.player.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.event.EventType;

/**
 * 登录事件
 *
 * @author xuewenkang
 * @date 2020/6/16 22:11
 */
public class LoginEvent implements Event {

    private final EventType type = EventType.LOGIN;

    private final Long playerId;

    public LoginEvent(Long playerId){
        this.playerId = playerId;
    }

    /**
     * 得到事件类型
     *
     * @return com.game.gameserver.event.EventType
     */
    @Override
    public EventType getEventType() {
        return type;
    }

    public Long getPlayerId() {
        return playerId;
    }
}
