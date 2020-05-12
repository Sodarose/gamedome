package com.game.gameserver.service;

import com.game.protocol.Message;
import io.netty.channel.Channel;

/**
 * @author xuewenkang
 * 游戏服务
 */
public abstract class AbstractGameService implements BaseService{

    /**
     * description: 处理客户端的场景请求
     *
     * @param message 消息体
     * @param channel 通道
     * @return void
     */
    public abstract void handleScene(Message message,Channel channel);

    @Override
    public String serviceName() {
        return "AbstractGameService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
