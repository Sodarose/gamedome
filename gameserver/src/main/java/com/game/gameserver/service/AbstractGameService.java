package com.game.gameserver.service;

import com.game.protocol.Message;
import io.netty.channel.Channel;

/**
 * @author xuewenkang
 * 游戏服务
 */
public abstract class AbstractGameService implements BaseService{

    /**
     * description: 处理初始化
     *
     * @param message 消息
     * @param channel 当前用户通道
     * @return void
     */
    public abstract void handleInitClient(Message message, Channel channel);

    @Override
    public String serviceName() {
        return "AbstractGameService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
