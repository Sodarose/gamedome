package com.game.gameserver.common.entity;

/**
 * @author xuewenkang
 * @date 2020/6/23 10:34
 */
public interface Unit {
    /**
     * 更新
     */
    void update();

    /**
     * 返回单位类型
     *
     * @param
     * @return int
     */
    int getUnitType();

    /**
     * 返回单位Id
     *
     * @param
     * @return long
     */
    long getUnitId();

    /**
     * 单位是否死亡
     *
     * @param
     * @return boolean
     */
    boolean isDead();
}
