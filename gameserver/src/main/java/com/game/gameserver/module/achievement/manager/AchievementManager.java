/*
package com.game.gameserver.module.achievement.manager;

import com.game.gameserver.common.config.AchievementConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.achievement.dao.AchievementMapper;
import com.game.gameserver.module.achievement.entity.Achievement;
import com.game.gameserver.module.achievement.entity.PlayerAchievement;
import com.game.gameserver.module.monster.event.MonsterDeadEvent;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.event.LoginEvent;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.task.entity.TaskProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

*/
/**
 * @author xuewenkang
 * @date 2020/7/1 21:01
 *//*

@Listener
@Component
public class AchievementManager {
    private final static Logger logger = LoggerFactory.getLogger(AchievementManager.class);

    */
/**
     * 成就本地缓存列表
     *//*

    private final Map<Long, PlayerAchievement> LOCAL_PLAYER_ACHIEVEMENT = new ConcurrentHashMap<>();

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private AchievementMapper achievementMapper;

    */
/**
     * 读取玩家成绩
     *
     * @param playerId
     * @return void
     *//*

    public void loadPlayerAchievement(long playerId) {
        Player player = playerManager.getPlayer(playerId);
        if (player == null) {
            return;
        }
        // 暂时先不从数据库中读取
        PlayerAchievement playerAchievement = new PlayerAchievement(playerId);
        */
/** 暂时填入测试数据  将符合等级要求的成就任务全部放入进去 *//*

        Map<Integer, AchievementConfig> achievementConfigMap = StaticConfigManager.getInstance().getAchievementConfigMap();
        for (Map.Entry<Integer, AchievementConfig> entry : achievementConfigMap.entrySet()) {
            // 符合等级要求
            if (entry.getValue().getLimitLevel() <= player.getLevel()) {
                // 创建任务
                Achievement achievement = Achievement.valueOf(playerId, entry.getValue());
                // 添加任务
                playerAchievement.add(achievement);
            }
        }
        LOCAL_PLAYER_ACHIEVEMENT.put(playerId, playerAchievement);
    }

    */
/**
     * 获取玩家成就
     *
     * @param playerId
     * @return com.game.gameserver.module.achievement.entity.PlayerAchievement
     *//*

    public PlayerAchievement getPlayerAchievement(long playerId) {
        if (LOCAL_PLAYER_ACHIEVEMENT.get(playerId) == null) {
            loadPlayerAchievement(playerId);
        }
        return LOCAL_PLAYER_ACHIEVEMENT.get(playerId);
    }

    */
/**
     * 处理用户角色登录事件 读取该角色成就信息
     *
     * @param event
     * @return void
     *//*

    @EventHandler(type = EventType.EVENT_TYPE_LOGIN)
    public void handleLoginEvent(LoginEvent event) {
        logger.info("处理登录事件----成就----读取用户成就数据");
        long playerId = event.getPlayerId();
        Player player = playerManager.getPlayer(playerId);
        if (player == null) {
            return;
        }
        // 读取用户成就信息
        loadPlayerAchievement(playerId);
    }

    */
/**
     * 处理怪物死亡事件 更新成就进度
     *
     * @param event
     * @return void
     *//*

    @EventHandler(type = EventType.EVENT_TYPE_MONSTER_DEAD)
    public void handleMonsterDeadEvent(MonsterDeadEvent event) {
        long playerId = event.getPlayerId();
        Player player = playerManager.getPlayer(playerId);
        if (player == null) {
            return;
        }
        // 获取用户成就
        PlayerAchievement playerAchievement = LOCAL_PLAYER_ACHIEVEMENT.get(playerId);
        if (playerAchievement == null) {
            return;
        }
        // 上锁
        Lock lock = playerAchievement.getWriteLock();
        lock.lock();
        try {
            // 获得所有进度列表
            List<TaskProgress> taskProgresses = playerAchievement.getAllTaskProgress();
            // 便利所有进度 更新进度
            for (TaskProgress taskProgress : taskProgresses) {
                // 过滤已经完成的进度
                if (taskProgress.isComplete()) {
                    continue;
                }
                // 判断行为和目标是否相等
                if (taskProgress.getType() == event.getEventType() &&
                        taskProgress.getTarget() == event.getMonsterConfigType()) {
                    // 更新进度
                    taskProgress.addNum(event.getValue());
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
*/
