package com.game.gameserver.module.email.model;

import com.game.gameserver.module.email.entity.EmailEntity;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 邮箱模型
 *
 * @author xuewenkang
 * @date 2020/6/18 21:38
 */
@Getter
public class EmailBox {
    /** 角色Id */
    private final long playerId;
    /** 邮件容器 */
    private final Map<Long,EmailEntity> emailMap;

    public EmailBox(long playerId){
        this.playerId = playerId;
        this.emailMap = new ConcurrentHashMap<>();
    }

    public void initialize(List<EmailEntity> emailEntities){
        for(EmailEntity emailEntity:emailEntities){
            emailMap.put(emailEntity.getId(),emailEntity);
        }
    }
}
