package com.game.gameserver.module.task.service;

import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.event.Event;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.*;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.equipment.service.EquipService;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.task.dao.TaskDbService;
import com.game.gameserver.module.task.helper.TaskHelper;
import com.game.gameserver.module.task.model.*;
import com.game.gameserver.module.task.entity.TaskEntity;
import com.game.gameserver.module.task.manager.TaskManager;
import com.game.gameserver.module.task.type.TaskState;
import com.game.gameserver.module.task.type.TaskType;
import com.game.gameserver.module.team.model.Team;
import com.game.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.event.ItemEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuewenkang
 * @date 2020/6/29 10:17
 */
@Listener
@Service
public class TaskService {

    private final static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private TaskDbService taskDbService;

    @Autowired
    private BackBagService backBagService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private EquipService equipService;

    private Task createTask(long playerId, int taskId) {
        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(taskId);
        Task task = new Task();
        task.setTaskId(taskId);
        task.setPlayerId(playerId);
        task.setState(TaskState.ACCEPTED);
        task.setTaskConfig(taskConfig);
        taskConfig.getTaskConditionMap().values().forEach(taskCondition -> {
            TaskProgress taskProgress = new TaskProgress(taskCondition.getTarget());
            task.getTaskProgressMap().put(taskProgress.getTarget(), taskProgress);
        });
        return task;
    }

    /**
     * @param player
     * @return void
     */
    public void loadPlayerTask(Player player) {
        UserTask userTask = new UserTask(player.getId());
        List<TaskEntity> taskEntities = taskDbService.selectTaskEntityList(player.getId());
        // 转化
        taskEntities.forEach(taskEntity -> {
            Task task = TaskHelper.transFromTask(taskEntity);
            userTask.getTaskMap().put(task.getTaskId(), task);
        });
        taskManager.putUserTask(player.getId(), userTask);
    }

    /**
     * 查看所有任务列表
     *
     * @param player
     * @return void
     */
    public void showAllTask(Player player) {
        // 获取任务资源MAP
        Map<Integer, TaskConfig> taskConfigMap = StaticConfigManager.getInstance().getTaskConfigMap();
        List<TaskConfig> taskConfigs = new ArrayList<>(taskConfigMap.values());
        // 返回任务信息信息
        NotificationHelper.notifyPlayer(player, TaskHelper.buildTaskConfigListMsg(taskConfigs));
    }

    /**
     * 显示用户当前可接受的任务
     *
     * @param player
     * @return TaskProtocol.QueryReceiveAbleTaskRes
     */
    public void showReceiveAbleTask(Player player) {
        // 获取用户任务容器
        UserTask userTask = taskManager.getUserTask(player.getPlayerEntity().getId());
        if (userTask == null) {
            NotificationHelper.notifyPlayer(player, "获取用户任务数据失败");
            return;
        }
        // 可接受任务列表
        List<TaskConfig> receiveAbleTasks = new ArrayList<>();
        // 获取任务资源MAP
        Map<Integer, TaskConfig> taskConfigMap = StaticConfigManager.getInstance().getTaskConfigMap();
        for (Map.Entry<Integer, TaskConfig> entry : taskConfigMap.entrySet()) {
            // 跳过未达到领取条件 并且已经领取过的任务
            if (entry.getValue().getLimitLevel() > player.getLevel() || userTask.getTaskMap()
                    .get(entry.getKey()) != null) {
                continue;
            }
            receiveAbleTasks.add(entry.getValue());
        }
        // 返回结果
        NotificationHelper.notifyPlayer(player, TaskHelper.buildTaskConfigListMsg(receiveAbleTasks));
    }

    /**
     * 显示用户已经接受的任务
     *
     * @param player
     * @return void
     */
    public void showReceiveTask(Player player) {
        UserTask userTask = taskManager.getUserTask(player.getPlayerEntity().getId());
        if (userTask == null) {
            NotificationHelper.notifyPlayer(player, "获取任务列表失败");
            return;
        }
        List<Task> tasks = new ArrayList<>(userTask.getTaskMap().values());
        NotificationHelper.notifyPlayer(player, TaskHelper.buildTaskListMsg(tasks));
    }


    /**
     * 接取任务
     *
     * @param player
     * @param taskId
     * @return void
     */
    public void acceptTask(Player player, int taskId) {
        UserTask userTask = taskManager.getUserTask(player.getPlayerEntity().getId());
        if (userTask == null) {
            NotificationHelper.notifyPlayer(player, "获取任务列表失败");
            return;
        }

        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(taskId);
        if (taskConfig == null) {
            NotificationHelper.notifyPlayer(player, "无该任务");
            return;
        }

        // 判断是否达到接收任务要求
        if (taskConfig.getLimitLevel() > player.getPlayerEntity().getLevel()) {
            NotificationHelper.notifyPlayer(player, "没有达到可接受任务的要求");
            return;
        }

        // 判断是否已经接受该任务
        if (userTask.getTaskMap().get(taskId) != null) {
            NotificationHelper.notifyPlayer(player, "您已经接受了该任务");
            return;
        }

        // 创建任务 放入任务容器中
        Task task = createTask(player.getId(), taskId);
        userTask.getTaskMap().put(task.getTaskId(), task);

        // 异步更新数据库
        taskDbService.insertAsync(TaskHelper.transFromTaskEntity(task));
        NotificationHelper.notifyPlayer(player, "接受任务成功");
    }

    /**
     * 取消任务
     *
     * @param player
     * @param taskId
     * @return void
     */
    public void cancelTask(Player player, int taskId) {
        UserTask userTask = taskManager.getUserTask(player.getPlayerEntity().getId());
        if (userTask == null) {
            NotificationHelper.notifyPlayer(player, "获取任务列表失败");
            return;
        }
        Task task = userTask.getTaskMap().get(taskId);
        if (task == null) {
            NotificationHelper.notifyPlayer(player, "你的任务列表中没有此任务");
            return;
        }
        if (task.getState() != TaskState.ACCEPTED) {
            NotificationHelper.notifyPlayer(player, "你不能取消非进行中的任务");
            return;
        }
        if (!task.getTaskConfig().isCancel()) {
            NotificationHelper.notifyPlayer(player, "该任务不能被取消");
            return;
        }
        // 移除该任务
        userTask.getTaskMap().remove(taskId);
        taskDbService.deleteAsync(taskId, player.getId());
        NotificationHelper.notifyPlayer(player, "取消任务");
    }

    /**
     * 提交任务
     *
     * @param taskId
     * @return void
     */
    public void submitTask(Player player, int taskId) {
        UserTask userTask = taskManager.getUserTask(player.getPlayerEntity().getId());
        if (userTask == null) {
            NotificationHelper.notifyPlayer(player, "获取任务列表失败");
            return;
        }
        // 判断任务是否为可提交状态
        Task task = userTask.getTaskMap().get(taskId);
        if (task == null) {
            NotificationHelper.notifyPlayer(player, "你没有接受此任务");
            return;
        }
        if (task.getState() != TaskState.COMPLETED) {
            NotificationHelper.notifyPlayer(player, "任务未完成或者已经完成并领取奖励");
            return;
        }
        // 判断背包容量是否足够
        task.getTaskConfig().getAwards().forEach(award -> {
            Item item = itemService.createItem(award.getItemId(), award.getNum());
            backBagService.addItem(player, item);
        });
        task.setState(TaskState.FINISH);
        // 异步更新数据
        taskDbService.updateAsync(TaskHelper.transFromTaskEntity(task));
        // 返回结果
        NotificationHelper.notifyPlayer(player, "领取成功");
    }


    /**
     * 返回正在进行中并且类型相同的任务
     *
     * @param userTask
     * @param taskType
     * @return java.util.List<com.game.gameserver.module.task.model.Task>
     */
    private List<Task> getTaskByType(UserTask userTask, TaskType taskType) {
        return userTask.getTaskMap().values().stream().filter(task ->
                task.getTaskConfig().getType() ==
                        taskType.getType() && task.getState().equals(TaskState.ACCEPTED))
                .collect(Collectors.toList());
    }

    public void updateTaskProgressByIncrease(Player player, TaskType taskType, int target, boolean increase, int num) {
        // 返回对应的任务列表
        UserTask userTask = taskManager.getUserTask(player.getId());
        if (userTask == null) {
            return;
        }
        // 得到当前类型的任务并且是正在执行中的任务
        List<Task> tasks = getTaskByType(userTask, taskType);
        // 遍历任务列表
        for (Task task : tasks) {
            // 得到目标进度值
            TaskProgress taskProgress = task.getTaskProgressMap().get(target);
            // 不存在或者已经完成 跳过
            if (taskProgress == null || taskProgress.isCompleted()) {
                continue;
            }
            if (increase) {
                // 增加任务进度
                taskProgress.addProgress(num);
            } else {
                // 设置任务进度
                taskProgress.setProgress(num);
            }
            // 判断进度是否完成
            TaskCondition taskCondition = task.getTaskConfig().getTaskConditionMap().get(target);
            // 判断进度 进度未完成 任务也就未完成 跳过下面计算
            if (taskProgress.getProgress() < taskCondition.getAmount()) {
                continue;
            }
            // 完成进度
            taskProgress.setCompleted(true);
            // 判断任务是否完成
            if (checkTaskComplete(task)) {
                task.setState(TaskState.COMPLETED);
                NotificationHelper.notifyPlayer(player, MessageFormat.format("完成任务:{0}",
                        task.getTaskConfig().getName()));
            }
            // 异步更新数据库
            taskDbService.updateAsync(TaskHelper.transFromTaskEntity(task));
        }
    }


    /*** 检查任务是否完成 */
    private boolean checkTaskComplete(Task task) {
        for (Map.Entry<Integer, TaskProgress> entry : task.getTaskProgressMap().entrySet()) {
            if (!entry.getValue().isCompleted()) {
                return false;
            }
        }
        return true;
    }

    @EventHandler
    public void handleTalkEvent(TalkEvent talkEvent) {
        int target = (int) talkEvent.getNpc().getNpcId();
        // 更新任务信息
        updateTaskProgressByIncrease(talkEvent.getPlayer(), TaskType.TALK_NPC, target, true, 1);
    }


    @EventHandler
    public void handleGoldsEvent(GoldsEvent goldsEvent) {
        int golds = goldsEvent.getPlayer().getPlayerEntity().getGolds();
        updateTaskProgressByIncrease(goldsEvent.getPlayer(),
                TaskType.GOLDS, 0, false, golds);
    }

    @EventHandler
    public void handleGuildEvent(GuildEvent guildEvent) {
        updateTaskProgressByIncrease(guildEvent.getPlayer(),
                TaskType.GUILD, 0, true, 1);
    }

    @EventHandler
    public void handleEquipChangeEvent(EquipmentChangeEvent event) {
        // 获取装备星级
        int star = equipService.getEquipBarStart(event.getPlayer());
        updateTaskProgressByIncrease(event.getPlayer(),
                TaskType.EQUIPMENT_CHANGE, 0, false, star);
    }

    @EventHandler
    public void handleTeamEvent(TeamEvent event) {
        updateTaskProgressByIncrease(event.getPlayer(), TaskType.TEAM, 0, true, 1);
    }


    @EventHandler
    public void handTeakCompetedEvent(TaskCompletedEvent event) {
        int target = event.getTask().getTaskId();
        updateTaskProgressByIncrease(event.getPlayer(), TaskType.COMPLETE_TASK, 0, true, 1);
    }
}
