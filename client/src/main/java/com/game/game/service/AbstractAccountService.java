package com.game.game.service;


import com.game.protocol.Message;

/**
 * @author xuewenkang
 */
public abstract class AbstractAccountService implements BaseService{


    /**
     * 用户登录
     * @param loginId 登录账号
     * @param password 登录密码
     * */
    public abstract void login(String loginId,String password);

    /**
     * 用户注册
     * @param loginId 注册账号
     * @param password 注册密码
     * */
    public abstract void register(String loginId,String password);

    /**
     * 处理登录结果
     * @param message 登录响应消息
     * */
    public abstract void handleLoginRes(Message message);

    /**
     * 处理注册结果
     * @param message 注册响应消息
     * */
    public abstract void handleRegisterRes(Message message);

    @Override
    public String serviceName() {
        return "AccountService";
    }

    @Override
    public String toString() {
        return serviceName();
    }
}
