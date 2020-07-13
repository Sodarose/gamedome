package com.game.gameserver.module.email.manager;

import com.game.gameserver.module.email.model.EmailBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/15 20:27
 */
@Component
public class EmailManager {

    private final static Logger logger = LoggerFactory.getLogger(EmailManager.class);
    /** 玩家邮件缓存 */
    private final static  Map<Long, EmailBox> LOCAL_EMAIL_BOX_MAP = new ConcurrentHashMap<>();

    public EmailBox getEmailBox(long playerId){
        return LOCAL_EMAIL_BOX_MAP.get(playerId);
    }

    public void removeEmailBox(long playerId){
        LOCAL_EMAIL_BOX_MAP.remove(playerId);
    }

    public void putEmailBox(long playerId,EmailBox emailBox){
        LOCAL_EMAIL_BOX_MAP.put(playerId,emailBox);
    }
}
