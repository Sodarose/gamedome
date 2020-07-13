package com.game.gameserver.module.user.manager;

import com.game.gameserver.module.user.module.User;
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
public class UserManager {
    public final static Logger logger = LoggerFactory.getLogger(UserManager.class);

    /**
     * 本地账户缓存
     */
    private final static Map<String, User> LOCAL_ACCOUNT_MAP = new ConcurrentHashMap<>();

    public User getUser(String loginId) {
        return LOCAL_ACCOUNT_MAP.get(loginId);
    }

    public void putUser(String loginId, User user) {
        LOCAL_ACCOUNT_MAP.put(loginId, user);
    }

    public void removeUser(String loginId){
        LOCAL_ACCOUNT_MAP.remove(loginId);
    }
}
