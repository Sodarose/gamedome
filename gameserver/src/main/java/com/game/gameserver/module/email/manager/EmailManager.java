package com.game.gameserver.module.email.manager;

import com.game.gameserver.module.email.entity.Email;
import com.game.gameserver.module.player.model.PlayerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/15 20:27
 */
@Component
public class EmailManager {

    private final static Logger logger = LoggerFactory.getLogger(EmailManager.class);

    /** 玩家邮件 */
    private Map<Long, List<Email>> playerEmailMap = new ConcurrentHashMap<>();

}
