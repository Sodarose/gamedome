package com.game.gameserver.module.account.manager;

import com.game.gameserver.module.account.entity.Account;
import com.game.protocol.AccountProtocol;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author xuewenkang
 * @date 2020/5/24 23:45
 */
public interface AccountManager {
    final AttributeKey<Account> ACCOUNT_ATTRIBUTE_KEY = AttributeKey.newInstance("ACCOUNT_ATTRIBUTE_KEY");
    AccountProtocol.LoginRes doLogin(String loginId, String password, Channel channel);
}
