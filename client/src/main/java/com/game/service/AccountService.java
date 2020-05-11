package com.game.service;

/**
 * @author xuwenkang
 */
public interface AccountService {

    /**
     * 用户登录
     * @param loginId 登录账号
     * @param password 登录密码
     * */
    void login(String loginId,String password);

    /**
     *  用户退出
     * */
    void logout();
}
