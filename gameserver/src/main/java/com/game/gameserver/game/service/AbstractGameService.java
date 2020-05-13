package com.game.gameserver.game.service;

import com.game.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;

/**
 * @author xuewenkang
 * 游戏服务
 */
public abstract class AbstractGameService implements BaseService{

    /**
     * description: 处理用户当前角色场景请求
     * @param message 消息
     * @param channel 用户Channel
     */
    public abstract void handleScene(Message message,Channel channel);


    /**
     * description: 处理用户当前角色请求
     * @param message 消息
     * @param channel 用户Channel
     */
    public abstract void handleRole(Message message,Channel channel);

    /**
     * description: 处理角色移动
     * @param message 消息
     * @param channel 用户Channel
     * @return void
     */
    public abstract void handleRoleMove(Message message,Channel channel);

    /**
     * description: 处理切换地图命令
     * @param message 消息
     * @param channel 用户Channel
     */
    public abstract void handleCutMap(Message message,Channel channel);

    @Override
    public String serviceName() {
        return "AbstractGameService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
