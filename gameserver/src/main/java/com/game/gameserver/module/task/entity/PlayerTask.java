package com.game.gameserver.module.task.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 玩家任务容器
 *
 * @author xuewenkang
 * @date 2020/6/29 16:19
 */
public class PlayerTask {
    /**
     * 最大可接取任务数
     */
    private final static int MAX_TASK = 20;

    /**
     * 容器所属角色
     */
    private final long playerId;
    /**
     * 已经接取的任务列表
     */
    private volatile Map<Integer, Task> taskMap;

    public PlayerTask(long playerId) {
        this.playerId = playerId;
        this.taskMap = new ConcurrentHashMap<>();
    }

    public void initialize(List<Task> tasks) {
        for (Task task : tasks) {
            taskMap.put(task.getTaskId(), task);
        }
    }

    public long getPlayerId() {
        return playerId;
    }

    public List<Task> getTaskList() {
        List<Task> tasks = new ArrayList<>();
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            tasks.add(entry.getValue());
        }
        return tasks;
    }

    public boolean hasTask(int taskId){
        return taskMap.containsKey(taskId);
    }

    public void putTask(Task task){
        taskMap.put(task.getTaskId(),task);
    }

    public void removeTask(int taskId){
        taskMap.remove(taskId);
    }

    public Task getTask(int taskId){
        return taskMap.get(taskId);
    }
}
