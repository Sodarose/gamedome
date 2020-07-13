package com.game.gameserver.module.email.model;

import com.game.gameserver.module.email.entity.EmailEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 邮箱
 *
 * @author xuewenkang
 * @date 2020/6/18 21:38
 */
public class EmailBox {
    private final List<EmailEntity> emailEntityList;
    private final ReentrantReadWriteLock lock;
    public EmailBox() {
        this.emailEntityList = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void initialize(List<EmailEntity> emailEntityList){
        this.emailEntityList.addAll(emailEntityList);
    }

    public void addEmail(EmailEntity emailEntity){
        emailEntityList.add(emailEntity);
    }

    public void clear(){
        emailEntityList.clear();
    }

    public List<EmailEntity> getEmailEntityList(){
        return emailEntityList;
    }
}
