package com.game.gameserver.service;


import com.game.protocol.Message;

/**
 * @author xuewenkang
 */
public interface AccountService extends BaseService{
    void login(Message message);
}
