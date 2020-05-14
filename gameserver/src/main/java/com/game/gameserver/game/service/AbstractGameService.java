package com.game.gameserver.game.service;

import com.game.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;

/**
 * 游戏服务 处理游戏逻辑
 * @author xuewenkang
 */
public abstract class AbstractGameService implements BaseService{

    /**
     * description: 获取场景
     * @param message 消息
     * @param channel 用户Channel
     */
    public abstract void handleScene(Message message,Channel channel);


    /**
     * description: 获取角色
     * @param message 消息
     * @param channel 用户Channel
     */
    public abstract void handleRole(Message message,Channel channel);

    /**
     * description: 角色移动
     * @param message 消息
     * @param channel 用户Channel
     * @return void
     */
    public abstract void handleRoleMove(Message message,Channel channel);

    /**
     * description: 切换地图
     * @param message 消息
     * @param channel 用户Channel
     */
    public abstract void handleCutMap(Message message,Channel channel);

    /**
     * description: 保存用户角色数据
     * @param message
     * @param channel
     * @return void
     */
    public abstract void handleSave(Message message,Channel channel);

    @Override
    public String serviceName() {
        return "AbstractGameService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
