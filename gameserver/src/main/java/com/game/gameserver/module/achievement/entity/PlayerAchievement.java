/*
package com.game.gameserver.module.achievement.entity;

import com.game.gameserver.module.task.entity.TaskProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

*/
/**
 * 玩家成就容器
 *
 * @author xuewenkang
 * @date 2020/7/1 20:53
 *//*

public class PlayerAchievement {

    */
/**
     * 角色Id
     *//*

    private final long playerId;
    */
/**
     * 成就列表
     *//*

    private volatile List<Achievement> achievements;
    */
/**
     * 新增的成就
     *//*

    private volatile List<Achievement> ADD;
    */
/**
     * 待更新的成就
     *//*

    private volatile List<Achievement> UPDATE;
    */
/**
     * 读写锁
     *//*

    private final ReentrantReadWriteLock lock;

    public PlayerAchievement(long playerId) {
        this.playerId = playerId;
        this.achievements = new ArrayList<>();
        this.ADD = new ArrayList<>();
        this.UPDATE = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public Achievement getAchievement(int achievementId) {
        for (Achievement achievement : achievements) {
            if (achievement.getAchievementId() == achievementId) {
                return achievement;
            }
        }
        return null;
    }

    public boolean hasAchievement(int achievementId) {
        for (Achievement achievement : achievements) {
            if (achievement.getAchievementId() == achievementId) {
                return true;
            }
        }
        return false;
    }

    public void add(Achievement achievement) {
        achievements.add(achievement);
    }

    public Lock getReadLock() {
        return lock.readLock();
    }

    public Lock getWriteLock() {
        return lock.writeLock();
    }

    */
/**
     * 获取所有成就的进度
     *
     * @param
     * @return java.util.List<com.game.gameserver.module.task.entity.TaskProgress>
     *//*

    public List<TaskProgress> getAllTaskProgress() {
        List<TaskProgress> taskProgresses = new ArrayList<>();
        for (Achievement achievement : achievements) {
            taskProgresses.addAll(achievement.getTaskProgresses());
        }
        return taskProgresses;
    }

    public long getPlayerId() {
        return playerId;
    }
}
*/
