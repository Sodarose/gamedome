package com.game.gameserver.game.account;

import com.game.protocol.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/5/18 17:56
 */
@Service
public class AccountService {

    @Autowired
    private AccountMapper mapper;

    /**
     * 账号登录
     * @param message 具体消息
     * @param channel channel 通道
     * @return void
     */
    public void login(Message message, Channel channel){

    }

    /**
     * 账户注册
     * @param message 消息
     * @param channel channel 通道
     * @return void
     */
    public void register(Message message,Channel channel){

    }


}
