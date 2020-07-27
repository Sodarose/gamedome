package com.game.gameserver.module.achievement.manager;

import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.LogoutEvent;
import com.game.gameserver.module.achievement.model.PlayerAchievement;
import com.game.gameserver.module.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/7/20 20:00
 */
@Listener
@Component
public class AchievementManager {
    private final static Logger logger = LoggerFactory.getLogger(AchievementManager.class);

    private final static Map<Long, PlayerAchievement> LOCAL_PLAYER_ACHIEVEMENT_MAP = new ConcurrentHashMap<>();

    public void putPlayerAchievement(long playerId,PlayerAchievement playerAchievement){
        LOCAL_PLAYER_ACHIEVEMENT_MAP.put(playerId,playerAchievement);
    }

    public PlayerAchievement getPlayerAchievement(long playerId){
        return LOCAL_PLAYER_ACHIEVEMENT_MAP.get(playerId);
    }

    public void removePlayerAchievement(long playerId){
        LOCAL_PLAYER_ACHIEVEMENT_MAP.remove(playerId);
    }


    /**
     * 处理角色退出事件 移除成就缓存
     *
     * @param logoutEvent
     * @return void
     */
    @EventHandler
    public void handlePlayerLogoutEvent(LogoutEvent logoutEvent){
        Player player = logoutEvent.getPlayer();
        removePlayerAchievement(player.getId());
    }
}
