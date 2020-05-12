package com.game.service;

import com.game.protocol.Message;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 17:04
 */
public abstract class AbstractGameService implements BaseService {

    /**
     * description: 初始化游戏客户端
     * 从服务器获取数据
     * @return void
     */
    public abstract void iniGameClient();

    /**
     * description: 请求当前场景数据
     */
    public abstract void requestScene();

    /**
     * description: 请求当前玩家角色数据
     *
     * @return com.game.pojo.Role
     */
    public abstract void requestGameRole();

    /**
     * description: 处理接受到的Scene数据
     *
     * @param message 数据
     * @return void
     */
    public abstract void handleSceneMessage(Message message);

    /**
     * description: 处理接受到的角色数据
     *
     * @param message 数据
     * @return void
     */
    public abstract void handleGameRoleMessage(Message message);

    @Override
    public String serviceName() {
        return "GameService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
