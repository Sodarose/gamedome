package com.game.gameserver.event;

/**
 * 角色行为事件
 *
 * @author xuewenkang
 * @date 2020/6/16 21:49
 */
public enum EventType {
    /** 角色登录事件 */
    LOGIN,
    /** 角色登出事件 */
    LOG_OUT,
    /** 角色掉线事件 */
    LOST,
    /** 角色进入副本事件 */
    ENTRY_INSTANCE,
    /** 角色退出副本事件 */
    EXIT_INSTANCE,
    /** 怪物死亡事件 */
    KILL_MONSTER;
}
