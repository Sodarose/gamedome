package com.game.gameserver.module.achievement.model;

import com.game.gameserver.module.achievement.entity.AchievementEntity;
import com.game.gameserver.module.task.model.TaskProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



public class PlayerAchievement {


    private final long playerId;

    private volatile List<AchievementEntity> achievements;

    private volatile List<AchievementEntity> ADD;

    private volatile List<AchievementEntity> UPDATE;


    private final ReentrantReadWriteLock lock;

    public PlayerAchievement(long playerId) {
        this.playerId = playerId;
        this.achievements = new ArrayList<>();
        this.ADD = new ArrayList<>();
        this.UPDATE = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public List<AchievementEntity> getAchievements() {
        return achievements;
    }

    public AchievementEntity getAchievement(int achievementId) {
        for (AchievementEntity achievement : achievements) {
            if (achievement.getAchievementId() == achievementId) {
                return achievement;
            }
        }
        return null;
    }

    public boolean hasAchievement(int achievementId) {
        for (AchievementEntity achievement : achievements) {
            if (achievement.getAchievementId() == achievementId) {
                return true;
            }
        }
        return false;
    }

    public void add(AchievementEntity achievement) {
        achievements.add(achievement);
    }

    public Lock getReadLock() {
        return lock.readLock();
    }

    public Lock getWriteLock() {
        return lock.writeLock();
    }

    public List<TaskProgress> getAllTaskProgress() {
        List<TaskProgress> taskProgresses = new ArrayList<>();
        for (AchievementEntity achievement : achievements) {
            /*taskProgresses.addAll(achievement.getTaskProgresses());*/
        }
        return taskProgresses;
    }

    public long getPlayerId() {
        return playerId;
    }
}
