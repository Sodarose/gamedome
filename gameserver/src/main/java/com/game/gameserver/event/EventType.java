package com.game.gameserver.event;

/**
 * 事件分类
 *
 * @author xuewenkang
 * @date 2020/6/16 21:49
 */
public enum EventType {
    /** 登录事件 */
    LOGIN,
    /** 登出事件 */
    LOG_OUT,
    /** 掉线事件 */
    LOST,
    /** 副本回收事件*/
    INSTANCE_RECOVERY,
    /** 进入副本事件 */
    ENTRY_INSTANCE,
    /** 退出副本事件 */
    EXIT_INSTANCE,
    /** 怪物死亡事件 */
    MONSTER_DEAD;
}
