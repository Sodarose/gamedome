package com.game.gameserver.module.account.service;

import com.game.gameserver.module.account.entity.Account;
import com.game.protocol.AccountProtocol;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author xuewenkang
 * @date 2020/5/24 23:45
 */
public interface AccountService {
    /** 在channel 中存储账号信息 */
    AttributeKey<Account> ACCOUNT_ATTRIBUTE_KEY = AttributeKey.newInstance("ACCOUNT_ATTRIBUTE_KEY");

    /**
     * 登录服务
     * @param loginId  账户ID
     * @param password 密码
     * @param channel  用户连接的Channel
     * @return com.game.protocol.AccountProtocol.LoginRes
     */
    AccountProtocol.LoginRes doLogin(String loginId, String password, Channel channel);
    

}
