package com.game.gameserver.module.email.entity;

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
    private final List<Email> emailList;
    private final ReentrantReadWriteLock lock;
    public EmailBox() {
        this.emailList = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void initialize(List<Email> emailList){
        this.emailList.addAll(emailList);
    }
}
