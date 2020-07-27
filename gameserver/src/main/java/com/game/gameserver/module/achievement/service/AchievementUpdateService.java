package com.game.gameserver.module.achievement.service;

import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.GoldsChangeEvent;
import com.game.gameserver.event.event.InstanceEvent;
import com.game.gameserver.event.event.LevelUpEvent;
import com.game.gameserver.event.event.MonsterDeadEvent;
import com.game.gameserver.module.achievement.dao.AchievementDbService;
import com.game.gameserver.module.achievement.manager.AchievementManager;
import com.game.gameserver.module.achievement.model.Achievement;
import com.game.gameserver.module.achievement.model.PlayerAchievement;
import com.game.gameserver.module.achievement.type.AchievementState;
import com.game.gameserver.module.achievement.type.AchievementType;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.task.model.TaskCondition;
import com.game.gameserver.module.task.model.TaskProgress;
import com.game.gameserver.module.task.type.TaskState;
import com.game.gameserver.module.task.type.TaskType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 成就更新服务
 *
 * @author xuewenkang
 * @date 2020/7/23 16:15
 */
@Service
@Listener
public class AchievementUpdateService {


    @Autowired
    private AchievementDbService achievementDbService;
    @Autowired
    private AchievementManager achievementManager;
    @Autowired
    private ItemService itemService;
    @Autowired
    private BackBagService backBagService;
    @Autowired
    private AchievementService achievementService;

    /**
     * 获得对应类型的成就
     *
     * @param playerAchievement
     * @param achievementType
     * @return java.util.List<com.game.gameserver.module.achievement.model.Achievement>
     */
    private List<Achievement> getAchievement(PlayerAchievement playerAchievement, AchievementType achievementType) {
        return playerAchievement.getAchievementMap().values().stream().filter(achievement ->
                achievement.getAchievementConfig().getType() == achievementType.getType() &&
                        achievement.getState() == AchievementState.ACCEPTED
        ).collect(Collectors.toList());
    }

    /**
     * 更新成就进度
     *
     * @param player
     * @param achievementType
     * @param target
     * @param increase
     * @param num
     * @return void
     */
    public void updateAchievementProgress(Player player, AchievementType achievementType,
                                          int target, boolean increase, int num) {
        PlayerAchievement playerAchievement = achievementManager.getPlayerAchievement(player.getId());
        if (playerAchievement == null) {
            return;
        }
        // 得到当前类型的任务并且是正在执行中的成就
        List<Achievement> achievements = getAchievement(playerAchievement, achievementType);
        for (Achievement achievement : achievements) {
            TaskProgress taskProgress = achievement.getTaskProgress().get(target);
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
            TaskCondition taskCondition = achievement.getAchievementConfig().getTaskConditionMap().get(target);
            // 判断进度 进度未完成 任务也就未完成 跳过下面计算
            if (taskProgress.getProgress() < taskCondition.getAmount()) {
                continue;
            }
            // 完成进度
            taskProgress.setCompleted(true);
            // 判断任务是否完成
            if (checkAchievementComplete(achievement)) {
                achievement.setState(TaskState.COMPLETED);
                NotificationHelper.notifyPlayer(player, MessageFormat.format("完成成就:{0}",
                        achievement.getAchievementConfig().getName()));
            }
        }
    }

    /**
     * 检查成就是否完成
     *
     * @param achievement
     * @return boolean
     */
    private boolean checkAchievementComplete(Achievement achievement) {
        for (Map.Entry<Integer, TaskProgress> entry : achievement.getTaskProgress().entrySet()) {
            if (!entry.getValue().isCompleted()) {
                return false;
            }
        }
        return true;
    }


    /**
     * 下面便是事件监听
     */

    @EventHandler
    public void handleMonsterDeadEvent(MonsterDeadEvent monsterDeadEvent) {
        Player player = monsterDeadEvent.getPlayer();
        Monster monster = monsterDeadEvent.getMonster();
        updateAchievementProgress(player, AchievementType.KILL_MONSTER,
                monster.getMonsterConfig().getId(), true, 1);
    }

    @EventHandler
    public void handleInstanceEvent(InstanceEvent instanceEvent) {
        Player player = instanceEvent.getPlayer();
        int instanceId = instanceEvent.getInstanceId();
        updateAchievementProgress(player, AchievementType.INSTANCE, instanceId, true, 1);
    }

    @EventHandler
    public void handleGoldsChangeEvent(GoldsChangeEvent goldsChangeEvent) {
        Player player = goldsChangeEvent.getPlayer();
        int golds = player.getGolds();
        updateAchievementProgress(player, AchievementType.GOLDS, 0, false, golds);
    }

    @EventHandler
    public void handleLevelUpEvent(LevelUpEvent levelUpEvent) {
        Player player = levelUpEvent.getPlayer();
        updateAchievementProgress(player, AchievementType.LEVEL_UP, 0, false, player.getLevel());
        PlayerAchievement playerAchievement = achievementManager.getPlayerAchievement(player.getId());
        achievementService.addAcceptAbleAchievement(player, playerAchievement);
    }
}
