package com.game.gameserver.module.email.manager;

import com.game.gameserver.module.email.dao.EmailMapper;
import com.game.gameserver.module.email.entity.Email;
import com.game.gameserver.module.email.entity.EmailBox;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Map<Long, EmailBox> playerEmailMap = new ConcurrentHashMap<>();

    @Autowired
    private EmailMapper emailMapper;

    public void loadPlayerEmail(Player player){
        // 读取邮件
        List<Email> emailList = emailMapper.getEmailList(player.getId());
        EmailBox emailBox = new EmailBox();
        // 初始化
        emailBox.initialize(emailList);
        playerEmailMap.put(player.getId(),emailBox);
    }
}
