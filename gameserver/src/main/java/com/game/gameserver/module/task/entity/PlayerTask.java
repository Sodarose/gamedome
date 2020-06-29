package com.game.gameserver.module.task.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 玩家任务容器
 *
 * @author xuewenkang
 * @date 2020/6/29 16:19
 */
public class PlayerTask {
    /** 最大可接取任务数 */
    private final static int MAX_TASK = 20;

    private final long playerId;
    private final List<Task> tasks;
    private final ReentrantReadWriteLock lock;

    public PlayerTask(long playerId){
        this.playerId = playerId;
        this.tasks = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void initialize(List<Task> tasks){
        this.tasks.addAll(tasks);
    }

    public long getPlayerId(){
        return playerId;
    }

    public List<Task> getTasks(){
        return tasks;
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public Lock getReadLock(){
        return lock.readLock();
    }

    public Lock getWriteLock(){
        return lock.writeLock();
    }

    public boolean hasSpace(){
        return tasks.size() == MAX_TASK;
    }
}
