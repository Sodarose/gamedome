package com.game.gameserver.task;

import com.game.gameserver.task.annotation.CmdHandler;
import com.game.gameserver.game.service.BaseService;
import com.game.protocol.Message;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 消息分发处理，将消息根据消息类型
 * 分发对应的handler处理
 * @author xuewenkang
 */
@Component
public class MessageDispatcher {

    private final static Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private final HashMap<Short,CmdExecutor> executors = new HashMap<>(16);

    /**
     * 根据消息中的cmd值，分发指定的CmdExecutor执行
     * @param message 消息
     * */
    public void dispatch(Message message, Channel channel){
        short cmd = message.getCmd();
        if(executors.get(cmd)==null){
            return;
        }
        executors.get(cmd).invoked(message,channel);
    }

    /**
     * 将服务中的方法注册证CmdExecutor
     * @param service 处理消息的服务
     * */
    public void registerService(BaseService service){
        logger.info("register service {}",service);
        Class clazz = service.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods){
           CmdHandler cmdHandler = method.getAnnotation(CmdHandler.class);
           if(cmdHandler ==null){
                continue;
           }
           short cmd = cmdHandler.cmd();
           CmdExecutor cmdExecutor = new CmdExecutor(cmd,method,service);
           executors.put(cmd,cmdExecutor);
        }
    }
}
