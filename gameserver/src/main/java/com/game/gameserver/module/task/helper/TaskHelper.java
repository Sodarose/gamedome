package com.game.gameserver.module.task.helper;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.module.task.model.Task;
import com.game.gameserver.module.task.model.TaskCondition;
import com.game.gameserver.module.task.type.TaskState;

import java.util.List;

/**
 * 任务帮助类
 * 同步任务信息
 *
 * @author xuewenkang
 * @date 2020/7/1 21:00
 */
public class TaskHelper {

    public static String buildTaskConfigListMsg(List<TaskConfig> taskConfigs) {
        StringBuilder sb = new StringBuilder("任务列表:").append("\n");
        taskConfigs.forEach(taskConfig -> {
            sb.append(buildTaskConfigMsg(taskConfig)).append("\n").append("\n");
        });
        return sb.toString();
    }

    public static String buildTaskConfigMsg(TaskConfig taskConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("任务Id:").append(taskConfig.getId()).append("\n");
        sb.append("任务名称:").append(taskConfig.getName()).append("\n");
        sb.append("任务介绍:").append(taskConfig.getDescription()).append("\n");
        sb.append("任务要求:").append("\n");
        taskConfig.getTaskConditionMap().forEach((key, value) -> {
            sb.append(value.getDesc()).append(":").append(value.getAmount()).append("\n");
        });
        sb.append("任务奖励:").append("\n");
        sb.append("经验奖励:").append(taskConfig.getExprAward()).append("\n");
        sb.append("金币奖励:").append(taskConfig.getGoldAward()).append("\n");
        sb.append("道具奖励:").append("\n");
        taskConfig.getItemAward().forEach(award -> {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(award.getItemId());
            sb.append(itemConfig.getName()).append(" x ").append(award.getNum()).append("\n");
        });
        return sb.toString();
    }

    public static String buildTaskListMsg(List<Task> tasks) {
        StringBuilder sb = new StringBuilder("任务列表:").append("\n");
        tasks.forEach(task -> {
            sb.append(buildTaskMsg(task)).append("\n").append("\n");
        });
        return sb.toString();
    }

    public static String buildTaskMsg(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("任务Id:").append(task.getTaskConfig().getId()).append("\n");
        sb.append("任务名称:").append(task.getTaskConfig().getName()).append("\n");
        sb.append("任务介绍:").append(task.getTaskConfig().getDescription()).append("\n");
        sb.append("任务状态:").append(buildTaskStateMsg(task.getState())).append("\n");
        sb.append("任务进度:").append("\n");
        task.getTaskProgress().values().forEach(taskProgress -> {
            TaskCondition taskCondition = task.getTaskConfig().getTaskConditionMap().get(taskProgress.getTarget());
            sb.append(taskCondition.getDesc())
                    .append(":\t").append(taskProgress.getProgress())
                    .append("/").append(taskCondition.getAmount()).append("\n");
        });
        sb.append("\n");
        sb.append("任务奖励:").append("\n");
        sb.append("经验奖励:").append(task.getTaskConfig().getExprAward()).append("\n");
        sb.append("金币奖励:").append(task.getTaskConfig().getGoldAward()).append("\n");
        sb.append("道具奖励:").append("\n");
        task.getTaskConfig().getItemAward().forEach(award -> {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(award.getItemId());
            sb.append(itemConfig.getName()).append(" x ").append(award.getNum()).append("\n");
        });
        return sb.toString();
    }

<<<<<<< HEAD
    public static Task transFromTask(TaskEntity taskEntity) {
        Task task = new Task();
        // 基本信息
        task.setTaskId(taskEntity.getTaskId());
        task.setPlayerId(task.getPlayerId());
        task.setState(task.getState());
        // 本地资源
        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(taskEntity.getTaskId());
        task.setTaskConfig(taskConfig);
        // 任务进度
        Map<Integer, TaskProgress> taskProgressMap = JSON.parseObject(taskEntity.getTaskProgresses(),
                new TypeReference<Map<Integer, TaskProgress>>() {
                });
        // 放入任务中
        task.getTaskProgressMap().putAll(taskProgressMap);
        return task;
    }

    public static TaskEntity transFromTaskEntity(Task task) {
        TaskEntity taskEntity = new TaskEntity();
        // 设置基本信息
        taskEntity.setTaskId(task.getTaskId());
        taskEntity.setPlayerId(task.getPlayerId());
        taskEntity.setState(task.getState());
        // 设置进度
        String taskProgressJson = JSON.toJSONString(task.getTaskProgressMap());
        taskEntity.setTaskProgresses(taskProgressJson);
        return taskEntity;
=======
    private static String buildTaskStateMsg(int state) {
        if (state == TaskState.ACCEPTED) {
            return "进行中";
        }
        if (state == TaskState.COMPLETED) {
            return "已完成";
        }
        if(state == TaskState.FINISH){
            return "已结束";
        }
        return "";
>>>>>>> dev
    }
}
