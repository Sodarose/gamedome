package com.game.gameserver.service;

/**
 * @author kangkang
 * 数据库服务
 */
public abstract class DbService implements BaseService{

    @Override
    public String serviceName() {
        return "DbService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
