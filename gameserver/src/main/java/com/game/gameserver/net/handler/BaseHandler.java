package com.game.gameserver.net.handler;

import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.protocol.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/24 17:31
 */
public abstract class BaseHandler implements Handler {
    protected final Map<Short, CmdExecutor> CMD_EXECUTOR = new HashMap(1);

    @Autowired
    MessageDispatcher messageDispatcher;

    public BaseHandler(){

    }

    /**
     * 得到模块key值
     * @param
     * @return int
     */
    protected  int getModuleKey(){
        Class clazz = getClass();
        ModuleHandler moduleHandler = (ModuleHandler) clazz.getAnnotation(ModuleHandler.class);
        if(moduleHandler==null){
            return -1;
        }
        return moduleHandler.module();
    };

    /**
     * 初始化
     * @param
     * @return void
     */
    protected  void initialize(){
        Method[] methods = getClass().getMethods();
        if(methods.length == 0){
            return;
        }
        for(Method method:methods){
            CmdHandler cmdHandler = method.getAnnotation(CmdHandler.class);
            if(cmdHandler==null){
                continue;
            }
            Short cmd = cmdHandler.cmd();
            CmdExecutor cmdExecutor = new CmdExecutor(cmd,method,this);
            putCmdExecutor(cmd,cmdExecutor);
        }
    };

    @PostConstruct
    public void init(){
        messageDispatcher.registerModuleHandle(this);
        this.initialize();
    }

    public void putCmdExecutor(Short cmd,CmdExecutor cmdExecutor){
        if(CMD_EXECUTOR.containsKey(cmd)){
            return;
        }
        CMD_EXECUTOR.put(cmd,cmdExecutor);
    }

    /**
     * 将消息按照CMD命令 分发给模块中指定的处理器处理
     * @param message
     * @param channel
     * @return void
     */
    @Override
    public void dispatcher(Message message, Channel channel) {
        Short cmd = message.getCmd();
        CmdExecutor cmdExecutor = CMD_EXECUTOR.get(cmd);
        if(cmdExecutor==null){
            return;
        }
        cmdExecutor.execute(message,channel);
    }
}
