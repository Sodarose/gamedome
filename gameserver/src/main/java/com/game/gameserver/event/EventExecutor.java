package com.game.gameserver.event;

import java.lang.reflect.Method;

/**
 * 事件执行器
 *
 * @author xuewenkang
 * @date 2020/6/16 21:55
 */
public class EventExecutor {
    /** 监听器 */
    private final Object listener;
    /** 监听的事件类型 */
    private final EventType type;
    /** 执行方法 */
    private final Method method;

    public EventExecutor(EventType type,Method method,Object listener){
        this.listener = listener;
        this.method = method;
        this.type = type;
    }

    public void execute(Event event){
        try{
            method.invoke(listener,event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
