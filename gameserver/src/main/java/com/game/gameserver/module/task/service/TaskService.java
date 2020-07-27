package com.game.gameserver.module.task.service;

import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.LogoutEvent;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.equipment.service.EquipService;
import com.game.gameserver.module.friend.service.FriendService;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerDataService;
import com.game.gameserver.module.task.dao.TaskDbService;
import com.game.gameserver.module.task.helper.TaskHelper;
import com.game.gameserver.module.task.model.*;
import com.game.gameserver.module.task.entity.TaskEntity;
import com.game.gameserver.module.task.manager.TaskManager;
import com.game.gameserver.module.task.type.TaskState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private PlayerDataService playerDataService;

    private Task createTask(long playerId, int taskId) {
        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(taskId);
        Task task = new Task();
        task.setTaskId(taskId);
        task.setPlayerId(playerId);
        task.setState(TaskState.ACCEPTED);
        task.setTaskConfig(taskConfig);
        // 初始化进度
        taskConfig.getTaskConditionMap().values().forEach(taskCondition -> {
            TaskProgress taskProgress = new TaskProgress(taskCondition.getTarget());
            task.getTaskProgress().put(taskProgress.getTarget(), taskProgress);
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
            Task task = new Task();
            BeanUtils.copyProperties(taskEntity, task);
            TaskConfig taskConfig = StaticConfigManager
                    .getInstance()
                    .getTaskConfigMap()
                    .get(taskEntity.getTaskId());
            task.setTaskConfig(taskConfig);
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
        taskDbService.insertAsync(task);
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
        List<Item> items = new ArrayList<>();
        task.getTaskConfig().getItemAward().forEach(award -> {
            Item item = itemService.createItem(award.getItemId(), award.getNum());
            items.add(item);
        });
        // 判断背包容器是否足够
        if(!backBagService.hasSpace(player,items)){
            NotificationHelper.notifyPlayer(player,"背包容量不足");
            return;
        }
        items.forEach(item -> {
            backBagService.addItem(player, item);
        });
        // 领取金币奖励
        player.goldsChange(task.getTaskConfig().getGoldAward());
        // 领取经验奖励
        playerDataService.addExpr(player,task.getTaskConfig().getExprAward());
        task.setState(TaskState.FINISH);
        // 异步更新数据
        taskDbService.updateAsync(task);
        // 返回结果
        NotificationHelper.syncBackBag(player);
        NotificationHelper.notifyPlayer(player, "领取成功");
    }

    @EventHandler
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        Player player = logoutEvent.getPlayer();
        taskManager.removeTask(player.getId());
    }
}
