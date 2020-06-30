package com.game.gameserver.module.task.manager;

import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.player.event.LoginEvent;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.task.dao.TaskMapper;
import com.game.gameserver.module.task.entity.PlayerTask;
import com.game.gameserver.net.annotation.ModuleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务管理器
 *
 * @author xuewenkang
 * @date 2020/6/29 16:20
 */
@Listener
@Component
public class TaskManager {
    private final static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    /**
     * 本地缓存
     */
    private volatile static Map<Long, PlayerTask> LOCAL_PLAYER_TASK_MAP = new ConcurrentHashMap<>();

    public Map<Long, PlayerTask> getPlayerTaskMap() {
        return LOCAL_PLAYER_TASK_MAP;
    }

    public PlayerTask getPlayerTask(long playerId) {

        return LOCAL_PLAYER_TASK_MAP.get(playerId);
    }

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private TaskMapper taskMapper;

    /**
     * 处理用户登录事件
     *
     * @param loginEvent
     * @return void
     */
    @EventHandler(type = EventType.LOGIN)
    public void handleLoginEvent(LoginEvent loginEvent) {
        logger.info("处理{} 用户登录事件",loginEvent.getPlayerId());
        long playerId = loginEvent.getPlayerId();
        loadPlayerTask(playerId);
    }

    private void loadPlayerTask(long playerId){
        logger.info("读取{} 用户任务数据",playerId);
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return;
        }
        PlayerTask playerTask = new PlayerTask(playerId);
        LOCAL_PLAYER_TASK_MAP.put(playerId, playerTask);
    }


}
