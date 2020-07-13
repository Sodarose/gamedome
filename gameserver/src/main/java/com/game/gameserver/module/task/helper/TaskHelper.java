package com.game.gameserver.module.task.helper;

import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.module.task.entity.Task;

import java.util.List;

/**
 * 任务帮助类
 * 同步任务信息
 *
 * @author xuewenkang
 * @date 2020/7/1 21:00
 */
public class TaskHelper {

    public static String buildTaskConfigListMsg(List<TaskConfig> taskConfigs){
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public static String buildTaskConfigMsg(TaskConfig taskConfig){
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public static String buildTaskListMsg(List<Task> tasks){
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    public static String buildTaskMsg(Task task){
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
