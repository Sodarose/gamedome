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
    private final List<Email> emails;
    private final ReentrantReadWriteLock lock;
    public EmailBox() {
        this.emails = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }
}
