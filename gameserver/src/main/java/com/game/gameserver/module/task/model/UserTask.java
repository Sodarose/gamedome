package com.game.gameserver.module.task.model;

import com.game.gameserver.module.task.entity.TaskEntity;
import com.game.gameserver.module.task.type.TaskState;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家任务容器
 *
 * @author xuewenkang
 * @date 2020/6/29 16:19
 */
@Data
public class UserTask {

    /** 容器所属角色 */
    private final long playerId;

    /** 任务表 */
    private Map<Integer,Task> taskMap;

    public UserTask(long playerId) {
        this.playerId = playerId;
        this.taskMap = new ConcurrentHashMap<>();
    }

    public long getPlayerId() {
        return playerId;
    }

}
