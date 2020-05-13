package com.game.gameserver.game.service;

/**
 * @author kangkang
 * 数据库服务
 */
public abstract class AbstractDbService implements BaseService{

    @Override
    public String serviceName() {
        return "AbstractDbService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
