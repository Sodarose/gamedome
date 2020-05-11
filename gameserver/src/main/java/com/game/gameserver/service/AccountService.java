package com.game.gameserver.service;


import com.game.protocol.Message;

/**
 * @author xuewenkang
 */
public abstract class AccountService implements BaseService{

    /**
     * 处理登录事件
     * @param message 消息载体
     * */
    public abstract void login(Message message);

    @Override
    public String serviceName() {
        return "AccountService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
