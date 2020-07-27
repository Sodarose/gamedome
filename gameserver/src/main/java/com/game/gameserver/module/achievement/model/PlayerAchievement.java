package com.game.gameserver.module.achievement.model;

import com.game.gameserver.module.achievement.entity.AchievementEntity;
import com.game.gameserver.module.task.model.TaskProgress;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author kangkang
 */
@Data
public class PlayerAchievement {

    private final long playerId;

    private final Map<Integer,Achievement> achievementMap = new HashMap<>();

    public PlayerAchievement(long playerId) {
        this.playerId = playerId;
    }

}
