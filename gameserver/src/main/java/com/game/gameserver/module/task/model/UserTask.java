package com.game.gameserver.module.task.model;

import com.game.gameserver.module.task.entity.TaskEntity;
import com.game.gameserver.module.task.type.TaskState;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家任务容器
 *
 * @author xuewenkang
 * @date 2020/6/29 16:19
 */
public class UserTask {
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
    private volatile List<TaskEntity> tasks;

    public UserTask(long playerId) {
        this.playerId = playerId;
        this.tasks = new ArrayList<>();
    }

    public void initialize(List<TaskEntity> tasks) {
        this.tasks.addAll(tasks);
    }

    public long getPlayerId() {
        return playerId;
    }

    public List<TaskEntity> getTaskList() {
        return tasks;
    }

    public boolean hasTask(int taskId) {
        for (TaskEntity task : tasks) {
            if (task.getTaskId() == taskId) {
                return true;
            }
        }
        return false;
    }

    public void addTask(TaskEntity task) {
        tasks.add(task);
    }

    public void removeTask(int taskId) {
        tasks.removeIf(task -> task.getTaskId() == taskId);
    }

    public TaskEntity getTask(int taskId) {
        for (TaskEntity task : tasks) {
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
     * @return java.util.List<com.game.gameserver.module.task.model.TaskProgress>
     */
    public List<TaskProgress> getAllTaskProgress() {
        List<TaskProgress> taskProgresses = new ArrayList<>();
        for (TaskEntity task : tasks) {
            // 进行中的任务
            if(task.getState()==TaskState.ACCEPTED){
                taskProgresses.addAll(task.getTaskProgresses());
            }
        }
        return taskProgresses;
    }
}
