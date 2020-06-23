package com.game.gameserver.common.entity;

/**
 * @author xuewenkang
 * @date 2020/6/23 10:34
 */
public interface Unit {
    /** 更新 */
    void update();
    /** 单位类型 */
    int getUnitType();
    /** 单位Id */
    long getUnitId();
}
