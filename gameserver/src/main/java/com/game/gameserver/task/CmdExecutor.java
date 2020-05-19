package com.game.gameserver.task;

import com.game.protocol.Message;
import io.netty.channel.Channel;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * cmd 任务执行器
 * @author xuewenkang
 */
@Getter
public class CmdExecutor {

    /**
     * 执行器对应的CMD
     * */
    private Short cmd;

    /**
     * 执行器对应的Method方法
     * */
    private Method method;

    /**
     * 执行对应的对象
     * */
    private Object object;

    public CmdExecutor(Short cmd,Method method,Object object){
        this.cmd = cmd;
        this.method = method;
        this.object = object;
    }

    public void execute(Message message, Channel channel){
        try {
           method.invoke(object,message,channel);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
