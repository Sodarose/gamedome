package com.game.gameserver.module.achievement.service;

import com.game.gameserver.common.config.AchievementConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.Listener;
import com.game.gameserver.event.event.LogoutEvent;
import com.game.gameserver.module.achievement.dao.AchievementDbService;
import com.game.gameserver.module.achievement.entity.AchievementEntity;
import com.game.gameserver.module.achievement.helper.AchievementHelper;
import com.game.gameserver.module.achievement.manager.AchievementManager;
import com.game.gameserver.module.achievement.model.Achievement;
import com.game.gameserver.module.achievement.model.PlayerAchievement;
import com.game.gameserver.module.achievement.type.AchievementState;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.service.ItemService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerDataService;
import com.game.gameserver.module.task.model.TaskProgress;
import com.game.gameserver.module.task.type.TaskState;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/20 20:00
 */
@Listener
@Service
public class AchievementService {

    @Autowired
    private AchievementDbService achievementDbService;
    @Autowired
    private AchievementManager achievementManager;
    @Autowired
    private ItemService itemService;
    @Autowired
    private BackBagService backBagService;
    @Autowired
    private PlayerDataService playerDataService;

    private Achievement createAchievement(long playerId, AchievementConfig achievementConfig) {
        Achievement achievement = new Achievement();
        achievement.setAchievementConfig(achievementConfig);
        achievement.setAchievementId(achievementConfig.getId());
        achievement.setPlayerId(playerId);
        achievement.setState(AchievementState.ACCEPTED);
        // 初始化进度
        achievementConfig.getTaskConditionMap().values().forEach(taskCondition -> {
            TaskProgress taskProgress = new TaskProgress(taskCondition.getTarget());
            achievement.getTaskProgress().put(taskProgress.getTarget(), taskProgress);
        });
        return achievement;
    }

    /**
     * 加载用户成就
     *
     * @param player
     * @return void
     */
    public void loadPlayerAchievement(Player player) {
        PlayerAchievement playerAchievement = new PlayerAchievement(player.getId());
        List<AchievementEntity> achievementEntities = achievementDbService
                .selectAchievement(player.getId());
        achievementEntities.forEach(achievementEntity -> {
            Achievement achievement = new Achievement();
            BeanUtils.copyProperties(achievementEntity, achievement);
            AchievementConfig achievementConfig = StaticConfigManager
                    .getInstance()
                    .getAchievementConfigMap()
                    .get(achievementEntity.getAchievementId());
            achievement.setAchievementConfig(achievementConfig);
            playerAchievement.getAchievementMap().put(achievement.getAchievementId(), achievement);
        });
        // 放入缓存
        achievementManager.putPlayerAchievement(player.getId(), playerAchievement);
        // 根据玩家等级 添加玩家可接受成就
        addAcceptAbleAchievement(player, playerAchievement);
    }

    public void addAcceptAbleAchievement(Player player, PlayerAchievement playerAchievement) {
        StaticConfigManager.getInstance().getAchievementConfigMap().forEach(
                (key, value) -> {
                    // 玩家等级大于成就所需等级 并且玩家并没有接受该成就
                    if (player.getLevel() >= value.getLimitLevel() && !playerAchievement.getAchievementMap()
                            .containsKey(value.getId())) {
                        addAchievement(playerAchievement, value);
                    }
                }
        );
    }

    /**
     * 添加成就
     *
     * @param playerAchievement
     * @param achievementConfig
     * @return void
     */
    private void addAchievement(PlayerAchievement playerAchievement, AchievementConfig achievementConfig) {
        Achievement achievement = createAchievement(playerAchievement.getPlayerId(), achievementConfig);
        playerAchievement.getAchievementMap().put(achievementConfig.getId(), achievement);
        achievementDbService.insertAsync(achievement);
    }

    public void showAchievement(Player player) {
        PlayerAchievement playerAchievement = achievementManager.getPlayerAchievement(player.getId());
        NotificationHelper.notifyPlayer(player, AchievementHelper.buildAchievementMsg(playerAchievement));
    }

    /**
     * @param player
     * @param achievementId
     * @return void
     */
    public void submitAchievement(Player player, int achievementId) {
        PlayerAchievement playerAchievement = achievementManager.getPlayerAchievement(player.getId());
        if (playerAchievement == null) {
            return;
        }
        Achievement achievement = playerAchievement.getAchievementMap().get(achievementId);
        if (achievement == null) {
            NotificationHelper.notifyPlayer(player, "你没有该成就任务");
            return;
        }
        if (achievement.getState() != TaskState.COMPLETED) {
            NotificationHelper.notifyPlayer(player, "任务未完成或者已经完成并领取奖励");
            return;
        }
        List<Item> items = new ArrayList<>();
        // 判断背包容量是否足够
        achievement.getAchievementConfig().getItemAward().forEach(award -> {
            Item item = itemService.createItem(award.getItemId(), award.getNum());
            items.add(item);
        });
        // 判断背包容器是否足够
        if (!backBagService.hasSpace(player, items)) {
            NotificationHelper.notifyPlayer(player, "背包容量不足");
            return;
        }
        items.forEach(item -> {
            backBagService.addItem(player, item);
        });
        // 领取金币奖励
        player.goldsChange(achievement.getAchievementConfig().getGoldAward());
        // 领取经验奖励
        playerDataService.addExpr(player, achievement.getAchievementConfig().getExprAward());
        achievement.setState(TaskState.FINISH);
        // 异步更新数据
        achievementDbService.updateAsync(achievement);
        // 返回结果
        NotificationHelper.syncPlayer(player);
        NotificationHelper.syncBackBag(player);
        NotificationHelper.notifyPlayer(player, "领取成功");
    }

    @EventHandler
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        Player player = logoutEvent.getPlayer();
        achievementManager.removePlayerAchievement(player.getId());
    }
}
