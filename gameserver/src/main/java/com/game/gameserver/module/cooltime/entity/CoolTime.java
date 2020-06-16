package com.game.gameserver.module.cooltime.entity;

/**
 * 冷却时间实体
 *
 * @author xuewenkang
 * @date 2020/6/3 12:28
 */
public interface CoolTime<T> {
    /**
     * CD 更新
     */
    void update();

    /**
     * 是否过期
     *
     * @param
     * @return boolean
     */
    boolean isExpire();
}