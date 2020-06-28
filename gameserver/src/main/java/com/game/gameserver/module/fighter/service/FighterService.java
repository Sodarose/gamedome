package com.game.gameserver.module.fighter.service;

import com.game.protocol.FighterProtocol;

/**
 * @author xuewenkang
 * @date 2020/6/22 10:07
 */
public interface FighterService {

    /**
     * 玩家请求发起攻击
     *
     * @param playerId
     * @param targetId
     * @param unitType
     * @return com.game.protocol.FighterProtocol.AttackRes
     */
    FighterProtocol.AttackRes playerAttackReq(long playerId,long targetId,int unitType);



    /**
     * 玩家使用技能
     *
     * @param playerId 玩家Id
     * @param targetId 目标Id
     * @param unitType 目标类型
     * @param skillId 技能
     * @return void
     */
    void playerUseSkill(long playerId, long targetId, int unitType, int skillId);

    /**
     * 怪物主动攻击
     *
     * @param monsterId 怪物Id
     * @param targetId 目标Id
     * @param unitType 目标类型
     * @param skillId 技能Id
     * @return void
     */
    void monsterUseSkill(long monsterId, long targetId, int unitType, int skillId);


    /**
     * 宝宝主动攻击
     *
     * @param petId
     * @param targetId
     * @param unitType
     * @param skillId
     * @return void
     */
    void petUseSkill(long petId, long targetId, int unitType, int skillId);
}
