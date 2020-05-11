package com.game.gameserver.handler;

import com.game.protocol.Message;

import java.lang.reflect.Method;

/**
 * @author xuewenkang
 * cmd 任务执行器
 */
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

    public void invoked(Message message){
        try {
            method.invoke(object,message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
