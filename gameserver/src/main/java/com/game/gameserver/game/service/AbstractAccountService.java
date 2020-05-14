package com.game.gameserver.game.service;


import com.game.protocol.Message;
import io.netty.channel.Channel;

/**
 * @author xuewenkang
 * @date: 2020/5/12 16:09
 */
public abstract class AbstractAccountService implements BaseService{

    /**
     * 处理登录事件
     * @param message 消息载体
     * @param channel 该用户通道
     * @return void
     * */
    public abstract void login(Message message, Channel channel);

    /**
     * 注册
     * @param message 注册信息载体
     * @param channel 该用户通道
     * @return void
     * */
    public abstract void register(Message message,Channel channel);

    @Override
    public String serviceName() {
        return "AbstractAccountService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
