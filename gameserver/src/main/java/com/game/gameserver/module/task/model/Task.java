package com.game.gameserver.module.task.model;

import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.module.task.entity.TaskEntity;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务业务模型
 *
 * @author xuewenkang
 * @date 2020/7/14 18:00
 */
@Data
public class Task extends TaskEntity {

    /** 任务本地资源 */
    private TaskConfig taskConfig;

}