package com.game.gameserver.module.task.manager;

import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.task.entity.PlayerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/29 16:20
 */
@Listener
@Component
public class TaskManager {
    private final static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    private final static Map<Long,PlayerTask> PLAYER_TASK_MAP = new ConcurrentHashMap<>();

    @EventHandler(type = EventType.KILL_MONSTER)
    public void killMonster(){

    }
}
