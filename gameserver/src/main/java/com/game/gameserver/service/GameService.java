package com.game.gameserver.service;

/**
 * @author xuewenkang
 * 游戏服务
 */
public abstract class GameService implements BaseService{

    @Override
    public String serviceName() {
        return "GameService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
