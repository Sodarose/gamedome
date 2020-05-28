package com.game.task;

import com.game.module.Handler;
import com.game.protocol.Message;
import com.game.task.annotation.ModuleHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息分发 将消息分发到对应的服务
 * @author xuewenkang
 */
@Component
public class MessageDispatcher {
    private final static Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private final Map<Short, Handler> moduleHandlers = new HashMap<>(16);

    /**
     * 根据消息中的cmd值，分发指定的CmdExecutor执行
     * @param message 消息
     * */
    public void dispatch(Message message, Channel channel){
        short module = message.getModule();
        Handler moduleHandler = moduleHandlers.get(module);
        if(moduleHandler ==null){
            return;
        }
        moduleHandler.dispatcher(message);
    }

    /**
     * 注册模块处理器
     * @param handler 模块处理器
     * */
    public void registerModuleHandle(Handler handler){
        logger.info("register module handle {}",handler);
        Class clazz = handler.getClass();
        ModuleHandler moduleHandler = (ModuleHandler) clazz.getAnnotation(ModuleHandler.class);
        if(moduleHandler==null){
            return;
        }
        moduleHandlers.put(moduleHandler.module(),handler);
    }
}
