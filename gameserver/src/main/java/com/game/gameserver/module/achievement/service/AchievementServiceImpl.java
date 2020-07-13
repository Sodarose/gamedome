/*
package com.game.gameserver.module.achievement.service;

import com.game.gameserver.common.config.AchievementConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.achievement.dao.AchievementMapper;
import com.game.gameserver.module.achievement.entity.Achievement;
import com.game.gameserver.module.achievement.entity.PlayerAchievement;
import com.game.gameserver.module.achievement.manager.AchievementManager;
import com.game.gameserver.module.achievement.type.AchievementState;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.AchievementProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

*/
/**
 * @author xuewenkang
 * @date 2020/7/1 21:18
 *//*

@Component
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private AchievementManager achievementManager;

    */
/**
     * 查看所有成就信息
     *
     * @return com.game.protocol.AchievementProtocol.QueryAchievementListRes
     *//*

    @Override
    public AchievementProtocol.QueryAchievementListRes queryAllAchievementMsg() {
        List<AchievementConfig> achievementConfigList = new ArrayList<>();
        Map<Integer, AchievementConfig> achievementConfigMap = StaticConfigManager.getInstance().getAchievementConfigMap();
        for (Map.Entry<Integer, AchievementConfig> entry : achievementConfigMap.entrySet()) {
            achievementConfigList.add(entry.getValue());
        }
        return ProtocolFactory.createQueryAchievementListRes(0,"success",achievementConfigList);
    }

    */
/**
     * 查看玩家的成就列表
     *
     * @param playerId
     * @return com.game.protocol.AchievementProtocol.QueryPlayerAchievementListRes
     *//*

    @Override
    public AchievementProtocol.QueryPlayerAchievementListRes queryPlayerAchievementList(long playerId) {
        PlayerAchievement playerAchievement = achievementManager.getPlayerAchievement(playerId);
        if(playerAchievement.getAchievements()==null){
            return AchievementProtocol.QueryPlayerAchievementListRes.newBuilder()
                    .setCode(1001)
                    .setMsg("获取角色成就数据出错")
                    .build();
        }
        return ProtocolFactory.createQueryPlayerAchievementListRes(0,"success",playerAchievement);
    }

    */
/**
     * 提交成就并领取奖励
     *
     * @param playerId
     * @param achievementId
     * @return com.game.protocol.AchievementProtocol.SubmitAchievementRes
     *//*

    @Override
    public AchievementProtocol.SubmitAchievementRes submitAchievement(long playerId, int achievementId) {
        // 获取用户成就数据
        PlayerAchievement playerAchievement = achievementManager.getPlayerAchievement(playerId);
        if (playerAchievement.getAchievements() == null) {
            return AchievementProtocol.SubmitAchievementRes.newBuilder()
                    .setCode(1001)
                    .setMsg("获取角色成就数据失败")
                    .build();
        }
        // 上锁
        Lock writeLock = playerAchievement.getWriteLock();
        writeLock.lock();
        try {
            // 判断是否有该成就
            Achievement achievement = playerAchievement.getAchievement(achievementId);
            if (achievement == null) {
                return AchievementProtocol.SubmitAchievementRes.newBuilder()
                        .setCode(1002)
                        .setMsg("你没有该成就任务")
                        .build();
            }
            // 判断成就状态
            if(achievement.getState()!= AchievementState.COMPLETED){
                return AchievementProtocol.SubmitAchievementRes.newBuilder()
                        .setCode(1003)
                        .setMsg("非法操作")
                        .build();
            }
            // 获取成就奖励 并放入背包(等待背包模块重构)

            // 返回成功
            return AchievementProtocol.SubmitAchievementRes.newBuilder()
                    .setCode(0)
                    .setMsg("提交成功")
                    .build();
        }finally {
            writeLock.unlock();
        }
    }
}
*/
