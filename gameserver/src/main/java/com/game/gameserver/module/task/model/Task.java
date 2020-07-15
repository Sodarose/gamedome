package com.game.gameserver.module.task.model;

import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.module.task.entity.TaskEntity;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务业务模型
 *
 * @author xuewenkang
 * @date 2020/7/14 18:00
 */
@Data
public class Task {
    /** 任务资源id*/
    private Integer taskId;

    /** 任务状态*/
    private Integer state;

    /** 任务所属的角色*/
    private Long playerId;

    /** 任务进度表  目标，任务进度*/
    private Map<Integer,TaskProgress> taskProgressMap;

    /** 任务本地资源 */
    private TaskConfig taskConfig;

    public Task(){
        this.taskProgressMap = new HashMap<>();
    }

}