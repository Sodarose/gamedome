package com.game.gameserver.event;

/**
 * 事件接口
 *
 * @author xuewenkang
 * @date 2020/6/9 17:00
 */
public interface Event {
    /**
     * 得到事件类型
     *
     * @param
     * @return com.game.gameserver.event.EventType
     */
    EventType getEventType();
}
