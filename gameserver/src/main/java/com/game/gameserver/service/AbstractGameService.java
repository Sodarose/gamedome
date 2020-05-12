package com.game.gameserver.service;

/**
 * @author xuewenkang
 * 游戏服务
 */
public abstract class AbstractGameService implements BaseService{

    @Override
    public String serviceName() {
        return "AbstractGameService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
