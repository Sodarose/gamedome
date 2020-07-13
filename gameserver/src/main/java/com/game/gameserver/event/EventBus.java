package com.game.gameserver.event;

import com.game.gameserver.context.ServerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 事件总线
 *
 * @author xuewenkang
 * @date 2020/6/16 21:56
 */
@Component
public class EventBus {
    private final static Logger logger = LoggerFactory.getLogger(EventBus.class);

    public final static EventBus EVENT_BUS = new EventBus();

    private EventBus() {

    }
    /** 事件 */
    private final Map<Integer, List<EventExecutor>> eventExecutorMap = new HashMap<>();
    /** 异步事件队列 */
    private LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    /**
     * 激活事件
     *
     * @param event
     * @return void
     */
    public void fire(Event event) {
        int type = event.getEventType();
        List<EventExecutor> executors = eventExecutorMap.get(type);
        if (executors == null || executors.size() == 0) {
            return;
        }
        // 执行
        for (EventExecutor executor : executors) {
            executor.execute(event);
        }
    }

    public void fireAsync(Event event){
        try {
            eventQueue.put(event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册事件
     *
     * @param type
     * @param eventExecutor
     * @return void
     */
    public void register(int type, EventExecutor eventExecutor) {
        if (eventExecutor == null) {
            return;
        }
        List<EventExecutor> executors = eventExecutorMap.get(type);
        if (executors == null) {
            executors = new ArrayList<>();
            executors.add(eventExecutor);
            eventExecutorMap.put(type, executors);
            return;
        }
        executors.add(eventExecutor);
    }

    @PostConstruct
    public void init() {
        Map<String, Object> listerObjectMap = ServerContext
                .getApplication().getBeansWithAnnotation(Listener.class);
        for (Map.Entry<String, Object> entry : listerObjectMap.entrySet()) {
            Class clazz = entry.getValue().getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                EventHandler eventHandler = method.getAnnotation(EventHandler.class);
                if (eventHandler == null) {
                    continue;
                }
                int type = eventHandler.type();
                EventExecutor executor = new EventExecutor(type, method, entry.getValue());
                EventBus.EVENT_BUS.register(type, executor);
            }
        }
    }

    private class EventWorker implements Runnable{

        @Override
        public void run() {
            while (true){
                try{
                    Event event = eventQueue.take();
                    fire(event);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
