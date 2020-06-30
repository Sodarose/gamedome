package com.game.gameserver.module.task.service.impl;

import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.config.TaskConfig;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.task.entity.PlayerTask;
import com.game.gameserver.module.task.entity.Task;
import com.game.gameserver.module.task.manager.TaskManager;
import com.game.gameserver.module.task.service.TaskService;
import com.game.gameserver.module.task.type.TaskState;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.TaskProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/29 10:17
 */
@Listener
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskManager taskManager;
    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private ItemService itemService;
    /**
     * 获取任务列表
     *
     * @param playerId
     * @return com.game.protocol.TaskProtocol.TaskListRes
     */
    @Override
    public TaskProtocol.QueryAllTaskRes queryAllTask(long playerId) {
        // 获取任务资源MAP
        Map<Integer, TaskConfig> taskConfigMap = StaticConfigManager.getInstance().getTaskConfigMap();
        List<TaskConfig> taskConfigs = new ArrayList<>();
        for (Map.Entry<Integer, TaskConfig> entry : taskConfigMap.entrySet()) {
            taskConfigs.add(entry.getValue());
        }
        return ProtocolFactory.createQueryAllTaskRes(0, "success", taskConfigs);
    }

    /**
     * 获取可接受的任务列表
     *
     * @param playerId
     * @return com.game.protocol.TaskProtocol.QueryReceiveAbleTaskRes
     */
    @Override
    public TaskProtocol.QueryReceiveAbleTaskRes queryReceiveAbleTask(long playerId) {
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return TaskProtocol.QueryReceiveAbleTaskRes.newBuilder().setCode(1000).setMsg("获取用户数据出错").build();
        }
        // 获取任务资源MAP
        Map<Integer, TaskConfig> taskConfigMap = StaticConfigManager.getInstance().getTaskConfigMap();
        // 可接受任务列表
        List<TaskConfig> receiveAbleTasks = new ArrayList<>();
        for (Map.Entry<Integer, TaskConfig> entry : taskConfigMap.entrySet()) {
            receiveAbleTasks.add(entry.getValue());
        }
        // 获取用户已经接受的任务
        PlayerTask playerTask = taskManager.getPlayerTask(playerId);
        if (playerTask == null) {
            return TaskProtocol.QueryReceiveAbleTaskRes.newBuilder().setCode(1001).setMsg("获取任务列表失败").build();
        }
        // 移除已经接收的任务||等级不够的任务
        receiveAbleTasks.removeIf(taskConfig -> playerTask.hasTask(taskConfig.getId()) ||
                taskConfig.getLimitLevel() > playerObject
                        .getPlayer().getLevel());
        // 返回结果
        return ProtocolFactory.createQueryReceiveAbleTaskRes(0, "success", receiveAbleTasks);
    }

    /**
     * 查询用户已经接受的任务
     *
     * @param playerId
     * @return com.game.protocol.TaskProtocol.PlayerTaskListRes
     */
    @Override
    public TaskProtocol.QueryReceiveTaskRes queryReceiveTask(long playerId) {
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return TaskProtocol.QueryReceiveTaskRes.newBuilder().setCode(1000).setMsg("获取用户数据失败").build();
        }
        PlayerTask playerTask = taskManager.getPlayerTask(playerId);
        if (playerTask == null) {
            return TaskProtocol.QueryReceiveTaskRes.newBuilder().setCode(1001).setMsg("获取任务列表失败").build();
        }
        return ProtocolFactory.creatQueryReceiveTaskRes(0, "success", playerTask);
    }

    /**
     * 接受任务
     *
     * @param playerId
     * @param taskId
     * @return com.game.protocol.TaskProtocol.AcceptTaskTes
     */
    @Override
    public TaskProtocol.AcceptTaskTes acceptTask(long playerId, int taskId) {
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return TaskProtocol.AcceptTaskTes.newBuilder().setCode(1000).setMsg("获取用户数据失败").build();
        }
        PlayerTask playerTask = taskManager.getPlayerTask(playerId);
        if (playerTask == null) {
            return TaskProtocol.AcceptTaskTes.newBuilder().setCode(1001).setMsg("获取玩家任务列表失败").build();
        }
        // 判断是否达到接收任务要求
        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(taskId);
        if (taskConfig == null) {
            return TaskProtocol.AcceptTaskTes.newBuilder().setCode(1002).setMsg("无该任务").build();
        }
        if (taskConfig.getLimitLevel() > playerObject.getPlayer().getLevel()) {
            return TaskProtocol.AcceptTaskTes.newBuilder().setCode(1003).setMsg("没有达到可接受任务的要求").build();
        }
        // 判断是否已经接受该任务
        if (playerTask.hasTask(taskId)) {
            return TaskProtocol.AcceptTaskTes.newBuilder().setCode(1004).setMsg("您已经接受了该任务").build();
        }
        // 创建任务 放入角色人物容器中
        Task task = new Task(playerId, taskConfig);
        playerTask.putTask(task);
        // 完成
        return TaskProtocol.AcceptTaskTes.newBuilder().setCode(0).setMsg("接受任务成功").build();
    }

    /**
     * 取消任务
     *
     * @param playerId
     * @param taskId
     * @return com.game.protocol.TaskProtocol.CancelTaskRes
     */
    @Override
    public TaskProtocol.CancelTaskRes cancelTask(long playerId, int taskId) {
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return TaskProtocol.CancelTaskRes.newBuilder().setCode(1000).setMsg("获取用户数据失败").build();
        }
        PlayerTask playerTask = taskManager.getPlayerTask(playerId);
        if (playerTask == null) {
            return TaskProtocol.CancelTaskRes.newBuilder().setCode(1001).setMsg("获取玩家任务列表失败").build();
        }
        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(taskId);
        if (taskConfig == null) {
            return TaskProtocol.CancelTaskRes.newBuilder().setCode(1002).setMsg("无该任务").build();
        }
        if (!playerTask.hasTask(taskId)) {
            return TaskProtocol.CancelTaskRes.newBuilder().setCode(1003).setMsg("你没有接受此任务").build();
        }
        // 判断任务是否可取消
        if (!taskConfig.isCancel()) {
            return TaskProtocol.CancelTaskRes.newBuilder().setCode(1004).setMsg("该任务不能取消").build();
        }
        // 移除该任务
        playerTask.removeTask(taskId);
        return TaskProtocol.CancelTaskRes.newBuilder().setCode(0).setMsg("取消任务成功").build();
    }

    /**
     * 提交任务
     *
     * @param playerId
     * @param taskId
     * @return com.game.protocol.TaskProtocol.SubmitTaskRes
     */
    @Override
    public TaskProtocol.SubmitTaskRes submitTask(long playerId, int taskId) {
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return TaskProtocol.SubmitTaskRes.newBuilder().setCode(1000).setMsg("获取用户数据失败").build();
        }
        PlayerTask playerTask = taskManager.getPlayerTask(playerId);
        if (playerTask == null) {
            return TaskProtocol.SubmitTaskRes.newBuilder().setCode(1001).setMsg("获取玩家任务列表失败").build();
        }
        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(taskId);
        if (taskConfig == null) {
            return TaskProtocol.SubmitTaskRes.newBuilder().setCode(1002).setMsg("无该任务").build();
        }
        // 判断任务是否为可提交状态
        Task task = playerTask.getTask(taskId);
        if (task == null) {
            return TaskProtocol.SubmitTaskRes.newBuilder().setCode(1003).setMsg("你没有接受此任务").build();
        }
        if(task.getState()!= TaskState.COMPLETED){
            return TaskProtocol.SubmitTaskRes.newBuilder().setCode(1004).setMsg("任务未完成或者已经完成并领取奖励").build();
        }
        // 调用道具服务 发送任务奖励(暂时等待重构)
        // 设置任务状态
        task.setState(TaskState.REWARDS);
        // 返回结果
        return TaskProtocol.SubmitTaskRes.newBuilder().setCode(0).setMsg("领取成功").build();
    }

    /**
     * 查看任务
     *
     * @param playerId
     * @param taskId
     * @return com.game.protocol.TaskProtocol.CheckTaskRes
     */
    @Override
    public TaskProtocol.CheckTaskRes checkTask(long playerId, int taskId) {
        PlayerObject playerObject = playerManager.getPlayerObject(playerId);
        if (playerObject == null) {
            return TaskProtocol.CheckTaskRes.newBuilder().setCode(1000).setMsg("获取用户数据失败").build();
        }
        PlayerTask playerTask = taskManager.getPlayerTask(playerId);
        if (playerTask == null) {
            return TaskProtocol.CheckTaskRes.newBuilder().setCode(1001).setMsg("获取玩家任务列表失败").build();
        }
        TaskConfig taskConfig = StaticConfigManager.getInstance().getTaskConfigMap().get(taskId);
        if (taskConfig == null) {
            return TaskProtocol.CheckTaskRes.newBuilder().setCode(1002).setMsg("无该任务").build();
        }
        Task task = playerTask.getTask(taskId);
        if (task == null) {
            return TaskProtocol.CheckTaskRes.newBuilder().setCode(1003).setMsg("你没有接受此任务").build();
        }
        TaskProtocol.TaskInfo taskInfo = ProtocolFactory.createTaskInfo(task);
        return TaskProtocol.CheckTaskRes.newBuilder().setCode(0).setMsg("success").setTaskInfo(taskInfo).build();
    }
}
