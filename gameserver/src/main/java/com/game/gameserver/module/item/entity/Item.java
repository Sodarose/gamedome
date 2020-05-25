package com.game.gameserver.module.item.entity;

/**
 * 道具接口
 * @author xuewenkang
 * @date 2020/5/25 17:53
 */
public interface Item {
    /** 使用前 */
    void beforeUse();
    /** 使用中 */
    void using();
    /** 使用后 */
    void afterUse();
}
