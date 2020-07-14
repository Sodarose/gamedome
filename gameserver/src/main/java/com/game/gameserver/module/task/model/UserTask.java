package com.game.gameserver.module.task.model;

import com.game.gameserver.module.task.entity.Task;
import com.game.gameserver.module.task.entity.TaskProgress;
import com.game.gameserver.module.task.type.TaskState;

import java.util.ArrayList;
import java.util.Iterator;
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
     * 任务列表
     */
    private volatile List<Task> tasks;

    public PlayerTask(long playerId) {
        this.playerId = playerId;
        this.tasks = new ArrayList<>();
    }

    public void initialize(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    public long getPlayerId() {
        return playerId;
    }

    public List<Task> getTaskList() {
        return tasks;
    }

    public boolean hasTask(int taskId) {
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                return true;
            }
        }
        return false;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(int taskId) {
        tasks.removeIf(task -> task.getTaskId() == taskId);
    }

    public Task getTask(int taskId) {
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                return task;
            }
        }
        return null;
    }

    /**
     * 获取所有进行中的任务的进度
     *
     * @param
     * @return java.util.List<com.game.gameserver.module.task.entity.TaskProgress>
     */
    public List<TaskProgress> getAllTaskProgress() {
        List<TaskProgress> taskProgresses = new ArrayList<>();
        for (Task task : tasks) {
            // 进行中的任务
            if(task.getState()==TaskState.ACCEPTED){
                taskProgresses.addAll(task.getTaskProgresses());
            }
        }
        return taskProgresses;
    }
}
