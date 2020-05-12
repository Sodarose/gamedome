package com.game.gameserver.service;


import com.game.protocol.Message;
import com.game.protocol.Protocol;

/**
 * @author xuewenkang
 */
public abstract class AbstractAccountService implements BaseService{

    /**
     * 处理登录事件
     * @param message 消息载体
     * @return 返回结果
     * */
    public abstract Message login(Message message);

    /**
     * 注册
     * @param message 注册信息载体
     * @return 返回结果
     * */
    public abstract Message register(Message message);

    @Override
    public String serviceName() {
        return "AbstractAccountService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
