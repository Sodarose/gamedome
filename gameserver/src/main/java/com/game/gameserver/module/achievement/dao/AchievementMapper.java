
package com.game.gameserver.module.achievement.dao;

import com.game.gameserver.module.achievement.entity.AchievementEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/1 21:18
 */

@Mapper
@Repository
public interface AchievementMapper {
    List<AchievementEntity> selectAchievementList(long playerId);

    AchievementEntity select(int achievementId,long playerId);

    int insert(AchievementEntity achievementEntity);

    int update(AchievementEntity achievementEntity);

    int delete(int achievementId,long playerId);
}
