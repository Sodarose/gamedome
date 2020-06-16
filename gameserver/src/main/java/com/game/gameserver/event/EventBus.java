package com.game.gameserver.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件总线
 *
 * @author xuewenkang
 * @date 2020/6/16 21:56
 */
public class EventBus {
    private final static Logger logger = LoggerFactory.getLogger(EventBus.class);

    public final static EventBus EVENT_BUS = new EventBus();

    private EventBus(){

    }

    private final Map<EventType, List<EventExecutor>> eventExecutorMap = new HashMap<>();

    /**
     * 激活事件
     *
     * @param event
     * @return void
     */
    public void fire(Event event){
        EventType type = event.getEventType();
        List<EventExecutor> executors = eventExecutorMap.get(type);
        if(executors==null||executors.size()==0){
            return;
        }
        // 执行
        for(EventExecutor executor:executors){
            executor.execute(event);
        }
    }

    /**
     * 注册事件
     *
     * @param type
     * @param eventExecutor
     * @return void
     */
    public void register(EventType type,EventExecutor eventExecutor){
        if(type==null||eventExecutor==null){
            return;
        }
        List<EventExecutor> executors = eventExecutorMap.get(type);
        if(executors==null){
            executors = new ArrayList<>();
            executors.add(eventExecutor);
            eventExecutorMap.put(type,executors);
            return;
        }
        executors.add(eventExecutor);
    }
}
