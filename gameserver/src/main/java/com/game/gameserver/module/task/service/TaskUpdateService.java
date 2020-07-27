package com.game.gameserver.module.task.service;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.*;
import com.game.gameserver.module.equipment.service.EquipService;
import com.game.gameserver.module.friend.model.PlayerFriend;
import com.game.gameserver.module.friend.service.FriendService;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.task.dao.TaskDbService;
import com.game.gameserver.module.task.manager.TaskManager;
import com.game.gameserver.module.task.model.Task;
import com.game.gameserver.module.task.model.TaskCondition;
import com.game.gameserver.module.task.model.TaskProgress;
import com.game.gameserver.module.task.model.UserTask;
import com.game.gameserver.module.task.type.TaskState;
import com.game.gameserver.module.task.type.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author xuewenkang
 * @date 2020/7/23 15:35
 */
@Listener
@Service
public class TaskUpdateService {
    @Autowired
    private TaskManager taskManager;

    @Autowired
    private TaskDbService taskDbService;

    @Autowired
    private EquipService equipService;

    @Autowired
    private FriendService friendService;

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

    private void updateTaskProgress(Player player, TaskType taskType, int target, boolean increase, int num) {
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
            TaskProgress taskProgress = task.getTaskProgress().get(target);
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
            taskDbService.updateAsync(task);
        }
    }


    /*** 检查任务是否完成 */
    private boolean checkTaskComplete(Task task) {
        for (Map.Entry<Integer, TaskProgress> entry : task.getTaskProgress().entrySet()) {
            if (!entry.getValue().isCompleted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 处理怪物死亡事件
     *
     * @param monsterDeadEvent
     * @return void
     */
    @EventHandler
    public void handleMonsterDeadEvent(MonsterDeadEvent monsterDeadEvent) {
        Player player = monsterDeadEvent.getPlayer();
        Monster monster = monsterDeadEvent.getMonster();
        updateTaskProgress(player, TaskType.KILL_MONSTER, monster.getMonsterConfig().getId(), true, 1);
    }

    /**
     * 处理升级事件
     *
     * @param levelUpEvent
     * @return void
     */
    @EventHandler
    public void handleLevelUpEvent(LevelUpEvent levelUpEvent) {
        Player player = levelUpEvent.getPlayer();
        updateTaskProgress(player, TaskType.LEVEL_UP, 0, false, player.getLevel());
    }

    /**
     * 对话
     */
    @EventHandler
    public void handleTalkEvent(TalkEvent talkEvent) {
        int target = (int) talkEvent.getNpc().getNpcId();
        // 更新任务信息
        updateTaskProgress(talkEvent.getPlayer(), TaskType.TALK_NPC, target, true, 1);
    }

    /**
     * 收集道具
     */
    @EventHandler
    public void handleAddItemEvent(AddItemEvent addItemEvent) {
        Player player = addItemEvent.getPlayer();
        Item item = addItemEvent.getItem();
        ItemConfig targetItemConfig = StaticConfigManager.getInstance()
                .getItemConfigMap().get(item.getItemConfigId());
        if (targetItemConfig == null) {
            return;
        }
        if (targetItemConfig.getType() == null) {
            return;
        }
        // 收集背包中的
        AtomicInteger num = new AtomicInteger(0);
        player.getBackBag().getItemMap().values().forEach(i -> {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(i.getItemConfigId());
            if (itemConfig != null) {
                if (itemConfig.getType().equals(ItemType.EQUIP.getType())) {
                    if (itemConfig.getStar().equals(targetItemConfig.getStar())) {
                        num.getAndIncrement();
                    }
                }
            }
        });
        if (targetItemConfig.getType().equals(ItemType.EQUIP.getType())) {
            updateTaskProgress(player, TaskType.COLLECT_BEST_EQUIP, targetItemConfig.getStar(), false, num.get());
        }
    }

    /**
     * 通关副本
     *
     * @param instanceEvent
     * @return void
     */
    @EventHandler
    public void handleInstanceEvent(InstanceEvent instanceEvent) {
        Player player = instanceEvent.getPlayer();
        int instanceId = instanceEvent.getInstanceId();
        updateTaskProgress(player, TaskType.INSTANCE, instanceId, true, 1);
    }

    /**
     * 装备变更
     *
     * @param event
     * @return void
     */
    @EventHandler
    public void handleEquipChangeEvent(EquipChangeEvent event) {
        // 获取装备星级
        int star = equipService.getEquipBarStart(event.getPlayer());
        updateTaskProgress(event.getPlayer(),
                TaskType.EQUIPMENT_CHANGE, 0, false, star);
    }

    @EventHandler
    public void handleFriendEvent(FriendEvent friendEvent) {
        Player player = friendEvent.getPlayer();
        PlayerFriend playerFriend = friendService.getPlayerFriend(player.getId());
        updateTaskProgress(player, TaskType.FRIEND, 0, false, playerFriend.getFriendMap().size());
    }

    @EventHandler
    public void handleTeamEvent(TeamEvent event) {
        updateTaskProgress(event.getPlayer(), TaskType.TEAM, 0, true, 1);
    }

    @EventHandler
    public void handleGuildEvent(GuildEvent guildEvent) {
        updateTaskProgress(guildEvent.getPlayer(),
                TaskType.GUILD, 0, true, 1);
    }


    @EventHandler
    public void handleTradeEvent(TradeEvent tradeEvent) {
        updateTaskProgress(tradeEvent.getPlayer(), TaskType.TREAD, 0, true, 1);
    }

    @EventHandler
    public void handlePkEvent(PKEvent pkEvent) {
        updateTaskProgress(pkEvent.getInitiator(), TaskType.PK, 0, true, 1);
    }


    @EventHandler
    public void handleGoldsEvent(GoldsChangeEvent goldsEvent) {
        int golds = goldsEvent.getPlayer().getGolds();
        updateTaskProgress(goldsEvent.getPlayer(),
                TaskType.GOLDS, 0, false, golds);
    }

    @EventHandler
    public void handTaskCompetedEvent(TaskCompletedEvent event) {
        int target = event.getTask().getTaskId();
        updateTaskProgress(event.getPlayer(), TaskType.COMPLETE_TASK, 0, true, 1);
    }


}
