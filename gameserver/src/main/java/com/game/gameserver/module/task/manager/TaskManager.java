package com.game.gameserver.module.task.manager;

import com.game.gameserver.event.Listener;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.task.dao.TaskMapper;
import com.game.gameserver.module.task.model.UserTask;
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
    private volatile static Map<Long, UserTask> LOCAL_PLAYER_TASK_MAP = new ConcurrentHashMap<>();

    public Map<Long, UserTask> getPlayerTaskMap() {
        return LOCAL_PLAYER_TASK_MAP;
    }

    public UserTask getPlayerTask(long playerId) {

        return LOCAL_PLAYER_TASK_MAP.get(playerId);
    }

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private TaskMapper taskMapper;

    private void loadPlayerTask(long playerId) {

    }


}
