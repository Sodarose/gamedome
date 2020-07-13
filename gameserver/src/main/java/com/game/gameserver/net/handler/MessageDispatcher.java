package com.game.gameserver.net.handler;

import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.message.Message;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息分发处理，根据消息中的模块值 分发到对应的模块
 * @author xuewenkang
 */
@Component
public class MessageDispatcher {

    private final static Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private final Map<Integer, Handler> moduleHandlers = new HashMap<>(16);

    /**
     * 根据消息中的cmd值，分发指定的CmdExecutor执行
     * @param message 消息
     * */
    public void dispatch(Message message, Channel channel){
        int module = message.getModule();
        Handler moduleHandler = moduleHandlers.get(module);
        if(moduleHandler==null){
            return;
        }
        moduleHandler.dispatcher(message,channel);
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
