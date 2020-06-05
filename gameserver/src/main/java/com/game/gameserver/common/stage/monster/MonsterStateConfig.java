package com.game.gameserver.module.monster.entity;

/**
 * @author xuewenkang
 * @date 2020/6/4 10:39
 */
public interface MonsterStateConfig {
    /**
     * 站立状态开始时间 key
     */
    String STAND_START_TIME = "STAND_START_TIME";
    /**
     * 站立状态持续时间
     */
    String STAND_DURATION = "STAND_DURATION";
    /**
     * 站立状态结束时间
     */
    String STAND_END_TIME = "STAND_END_TIME";
    /**
     * 巡逻装填开始时间
     */
    String PATROL_STATE_TIME = "PATROL_STATE_TIME";
    /**
     * 巡逻持续时间
     */
    String PATROL_DURATION = "PATROL_DURATION";
    /**
     * 巡逻状态结束时间
     */
    String PATROL_END_TIME = "PATROL_END_TIME";

    int STAND = 0;
    int PATROL = 1;
    int RECOVER = 2;
    int ATTACK = 3;
    int DEATH = 4;
}
