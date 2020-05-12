package com.game.gameserver.handler;

import com.game.protocol.Message;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author xuewenkang
 * cmd 任务执行器
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

    public void invoked(Message message, Channel channel){
        try {
           method.invoke(object,message,channel);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
