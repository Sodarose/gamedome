package com.game.gameserver.module.achievement.model;

import com.game.gameserver.common.config.AchievementConfig;
import com.game.gameserver.module.achievement.entity.AchievementEntity;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/20 20:04
 */
@Data
public class Achievement extends AchievementEntity {
    /** 成就本地资源 */
    private AchievementConfig achievementConfig;
}
