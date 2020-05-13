package com.game.game.service;

import com.game.protocol.Message;
import com.game.protocol.Protocol;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 17:04
 */
public abstract class AbstractGameService implements BaseService {

    /**
     * description: 处理服务器返回的场景信息
     *
     * @param message 场景数据
     * @return void
     */
    public abstract void handleSceneMessage(Message message);

    /**
     * description: 处理服务返回的角色数据
     *
     * @param message 角色数据
     * @return void
     */
    public abstract void handleRoleMessage(Message message);

    /**
     * description:
     *
     * @param message 场景信息
     * @return void
     */
    public abstract void handleCutSceneMessage(Message message);


    /**
     * description: 处理服务器返回的移动场景回复
     *
     * @param message
     * @return void
     */
    public abstract void handleMoveSceneMessage(Message message);

    @Override
    public String serviceName() {
        return "GameService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
