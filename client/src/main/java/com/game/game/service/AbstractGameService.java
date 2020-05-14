package com.game.game.service;

import com.game.protocol.Message;
import com.game.protocol.Protocol;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 17:04
 */
public abstract class AbstractGameService implements BaseService {

    /**
     * 处理场景数据
     * @param message 场景数据
     * @return void
     */
    public abstract void handleSceneMessage(Message message);

    /**
     * 处理角色数据
     * @param message 角色数据
     * @return void
     */
    public abstract void handleRoleMessage(Message message);

    /**
     * 处理场景信息
     * @param message 场景信息
     * @return void
     */
    public abstract void handleCutSceneMessage(Message message);

    /**
     * 刷新页面
     * @param message 刷新当前场景
     * @return void
     */
    public abstract void handleRefreshMessage(Message message);

    /**
     * 处理移动信息
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
