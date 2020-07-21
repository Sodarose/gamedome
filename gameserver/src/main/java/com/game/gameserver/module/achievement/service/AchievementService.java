package com.game.gameserver.module.achievement.service;

import com.game.gameserver.module.achievement.dao.AchievementDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/7/20 20:00
 */
@Service
public class AchievementService {

    @Autowired
    private AchievementDbService achievementDbService;
}
