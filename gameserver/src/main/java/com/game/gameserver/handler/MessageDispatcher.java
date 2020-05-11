package com.game.gameserver.handler;

import com.game.gameserver.service.BaseService;
import com.game.protocol.Message;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author xuewenkang
 * 消息分发 将消息分发到对应的服务
 */
@Component
public class MessageDispatcher {

    private HashMap<Short,CmdExecutor> executors = new HashMap<>();

    public void dispatch(Message message){

    }

    public void registerService(BaseService service){
        Class clazz = service.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods){

        }
    }



}
