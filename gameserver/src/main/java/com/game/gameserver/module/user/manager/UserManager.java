package com.game.gameserver.module.user.manager;

import com.game.gameserver.module.user.module.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/7/11 1:47
 */
@Component
public class AccountManager {
    public final static Logger logger = LoggerFactory.getLogger(AccountManager.class);

    /**
     * 本地账户缓存
     */
    private final static Map<String, Account> LOCAL_ACCOUNT_MAP = new ConcurrentHashMap<>();

    public Account getAccount(String loginId) {
        return LOCAL_ACCOUNT_MAP.get(loginId);
    }

    public void putAccount(String loginId, Account account) {
        LOCAL_ACCOUNT_MAP.put(loginId,account);
    }

    public void removeAccount(String loginId){
        LOCAL_ACCOUNT_MAP.remove(loginId);
    }
}
