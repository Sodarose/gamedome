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
     * 本地任务缓存
     */
    private volatile static Map<Long, UserTask> LOCAL_USER_TASK_MAP = new ConcurrentHashMap<>();

    public Map<Long, UserTask> getPlayerTaskMap() {
        return LOCAL_USER_TASK_MAP;
    }

    public UserTask getUserTask(long playerId) {
        return LOCAL_USER_TASK_MAP.get(playerId);
    }

    public void putUserTask(long playerId,UserTask userTask){
        LOCAL_USER_TASK_MAP.put(playerId,userTask);
    }

    public void removeTask(long playerId){
        LOCAL_USER_TASK_MAP.remove(playerId);
    }
}
