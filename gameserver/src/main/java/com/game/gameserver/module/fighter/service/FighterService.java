package com.game.gameserver.module.fighter.service;

/**
 * @author xuewenkang
 * @date 2020/6/22 10:07
 */
public interface FighterService {


    /**
     * 玩家主动攻击
     *
     * @param playerId 玩家Id
     * @param targetId 目标Id
     * @param unitType 目标类型
     * @param skillId 技能Id
     * @return void
     */
    void playerAttack(long playerId,long targetId,int unitType,int skillId);


    /**
     * 怪物主动攻击
     *
     * @param monsterId 怪物Id
     * @param targetId 目标Id
     * @param unitType 目标类型
     * @param skillId 技能Id
     * @return void
     */
    void monsterAttack(long monsterId,long targetId,int unitType,int skillId);


    /**
     * 宝宝主动攻击
     *
     * @param petId
     * @param targetId
     * @param unitType
     * @param skillId
     * @return void
     */
    void petAttack(long petId,long targetId,int unitType,int skillId);
}
