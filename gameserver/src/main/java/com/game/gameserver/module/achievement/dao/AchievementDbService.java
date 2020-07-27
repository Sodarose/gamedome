package com.game.gameserver.module.achievement.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.achievement.entity.AchievementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/20 19:57
 */
@Repository
public class AchievementDbService extends BaseDbService {

    @Autowired
    private AchievementMapper achievementMapper;

    public List<AchievementEntity> selectAchievement(long playerId){
        return achievementMapper.selectAchievementList(playerId);
    }

    public AchievementEntity select(int achievementId,long playerId){
        return achievementMapper.select(achievementId,playerId);
    }

    public int insert(AchievementEntity achievementEntity){
        return achievementMapper.insert(achievementEntity);
    }

    public int update(AchievementEntity achievementEntity){
        return achievementMapper.update(achievementEntity);
    }

    public int delete(int achievementId,long playerId ){
        return achievementMapper.delete(achievementId,playerId);
    }

    public void insertAsync(AchievementEntity achievementEntity){
        submit(()->{
            int i = insert(achievementEntity);
        });
    }

    public void updateAsync(AchievementEntity achievementEntity){
        submit(()->{
            int i = update(achievementEntity);
        });
    }

    public void deleteAsync(int achievementId,long playerId){
        submit(()->{
            int i = delete(achievementId,playerId);
        });
    }
}
