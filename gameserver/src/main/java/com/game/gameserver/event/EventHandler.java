package com.game.gameserver.event;

import java.lang.annotation.*;

/**
 * 事件处理
 *
 * @author xuewenkang
 * @date 2020/6/16 21:47
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    EventType type() ;
}
