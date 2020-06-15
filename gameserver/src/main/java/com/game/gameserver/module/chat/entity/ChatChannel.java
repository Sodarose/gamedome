package com.game.gameserver.module.chat.entity;

import com.game.gameserver.util.GameUUID;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 频道
 * @author xuewenkang
 * @date 2020/6/15 15:50
 */
public class ChatChannel {
    /** channelId */
    private final Long chanelId;
    /** 频道内的玩家Id */
    private final List<Long> players;
    /** 读写锁 */
    private final ReentrantReadWriteLock lock;

    public ChatChannel(Long chanelId){
        this.chanelId = chanelId;
        this.players = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public Long getChanelId(){
        return chanelId;
    }

    public List<Long> getPlayers(){
        return players;
    }

    public ReentrantReadWriteLock lock(){
        return lock;
    }

    public Lock getReadLock(){
        return lock.readLock();
    }

    public Lock getWriteLock(){
        return lock.writeLock();
    }
}
