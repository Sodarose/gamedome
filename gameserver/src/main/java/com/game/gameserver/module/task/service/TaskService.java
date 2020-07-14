package com.game.gameserver.module.task.service;

import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.task.helper.TaskHelper;
import com.game.gameserver.module.task.model.UserTask;
import com.game.gameserver.module.task.entity.TaskEntity;
import com.game.gameserver.module.task.manager.TaskManager;
import com.game.gameserver.module.task.type.TaskState;
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

    @Autowired
    private TaskManager taskManager;
    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private BackBagService backBagService;


    /**
     * 查看所有任务列表
     *
     * @param player
     * @return void
     */
    public void showAllTask(Player player) {
        // 获取任务资源MAP
        Map<Integer, TaskConfig> taskConfigMap = StaticConfigManager.getInstance().getTaskConfigMap();
        List<TaskConfig> taskConfigs = new ArrayList<>();
        taskConfigMap.forEach((key,value)->{
            taskConfigs.add(value);
        });
        // 返回信息
        NotificationHelper.notifyPlayer(player, TaskHelper.buildTaskConfigListMsg(taskConfigs));
    }

    /**
     * 更改下顺序
     *
     * @param player
     * @return TaskProtocol.QueryReceiveAbleTaskRes
     */
    public void showReceiveAbleTask(Player player) {
        // 获取用户已经接受的任务
        UserTask playerTask = taskManager.getPlayerTask(player.getPlayerEntity().getId());
        if (playerTask == null) {
            return;
        }
        // 获取任务资源MAP
        Map<Integer, TaskConfig> taskConfigMap = StaticConfigManager.getInstance().getTaskConfigMap();
        // 可接受任务列表
        List<TaskConfig> receiveAbleTasks = new ArrayList<>();
        for (Map.Entry<Integer, TaskConfig> entry : taskConfigMap.entrySet()) {

            receiveAbleTasks.add(entry.getValue());
        }
        // 移除已经接收的任务||等级不够的任务
        receiveAbleTasks.removeIf(taskConfig -> playerTask.hasTask(taskConfig.getId()) ||
                taskConfig.getLimitLevel() > player.getPlayerEntity().getLevel());
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
        UserTask playerTask = taskManager.getPlayerTask(player.getPlayerEntity().getId());
        if (playerTask == null) {
            NotificationHelper.notifyPlayer(player,"获取任务列表失败");
            return;
        }
        NotificationHelper.notifyPlayer(player,"");
    }

    /**
     * 接取任务
     *
     * @param player
     * @param taskId
     * @return void
     */
    public void acceptTask(Player player, int taskId) {
        UserTask playerTask = taskManager.getPlayerTask(player.getPlayerEntity().getId());
        if (playerTask == null) {
            NotificationHelper.notifyPlayer(player,"获取任务列表失败");
            return;
        }

        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(taskId);
        if (taskConfig == null) {
            NotificationHelper.notifyPlayer(player,"无该任务");
            return;
        }

        // 判断是否达到接收任务要求
        if (taskConfig.getLimitLevel() > player.getPlayerEntity().getLevel()) {
            NotificationHelper.notifyPlayer(player,"没有达到可接受任务的要求");
            return;
        }

        // 判断是否已经接受该任务
        if (playerTask.hasTask(taskId)) {
            NotificationHelper.notifyPlayer(player,"您已经接受了该任务");
            return;
        }

        // 创建任务 放入角色人物容器中
        TaskEntity task = new TaskEntity(player.getPlayerEntity().getId(), taskConfig);
        playerTask.addTask(task);
        // 完成
        NotificationHelper.notifyPlayer(player,"接受任务成功");
    }

    /**
     * 取消任务
     *
     * @param player
     * @param taskId
     * @return void
     */
    public void cancelTask(Player player, int taskId) {
        UserTask playerTask = taskManager.getPlayerTask(player.getPlayerEntity().getId());
        if (playerTask == null) {
            NotificationHelper.notifyPlayer(player,"获取任务列表失败");
            return;
        }

        // 这里要改动
        if (!playerTask.hasTask(taskId)) {
            NotificationHelper.notifyPlayer(player,"你没有接受此任务");
            return;
        }

        // 判断任务是否可取消

        // 移除该任务
        playerTask.removeTask(taskId);
        NotificationHelper.notifyPlayer(player,"取消任务");
    }

    /**
     * 提交任务
     *
     * @param taskId
     * @return void
     */
    public void submitTask(Player player, int taskId) {
        UserTask playerTask = taskManager.getPlayerTask(player.getPlayerEntity().getId());
        if (playerTask == null) {
            NotificationHelper.notifyPlayer(player,"获取任务列表失败");
            return;
        }

        // 判断任务是否为可提交状态
        TaskEntity task = playerTask.getTask(taskId);
        if (task == null) {
            NotificationHelper.notifyPlayer(player,"你没有接受此任务");
            return;
        }

        if(task.getState()!= TaskState.COMPLETED){
            NotificationHelper.notifyPlayer(player,"任务未完成或者已经完成并领取奖励");
            return;
        }

        // 生成任务奖励
        // 放入背包

        // 设置任务状态
        task.setState(TaskState.FINISH);

        // 返回结果
        NotificationHelper.notifyPlayer(player,"领取成功");
    }

    /**
     * 展示任务
     *
     * @param player
     * @param taskId
     * @return void
     */
    public void showTask(Player player, int taskId) {
        UserTask playerTask = taskManager.getPlayerTask(player.getPlayerEntity().getId());
        if (playerTask == null) {
            NotificationHelper.notifyPlayer(player,"获取任务列表失败");
            return;
        }
        TaskEntity task = playerTask.getTask(taskId);
        if (task == null) {
            NotificationHelper.notifyPlayer(player,"你没有接受此任务");
            return;
        }
        NotificationHelper.notifyPlayer(player,"你没有接受此任务");
    }
}
