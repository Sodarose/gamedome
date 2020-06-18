package com.game.gameserver.module.player.model;

/**
 * 玩家状态
 *
 * @author xuewenkang
 * @date 2020/6/17 14:19
 */
public interface PlayerState {
    /** 在线状态 */
    int ONLINE = 0;
    /** 掉线状态 */
    int LOST = 1;
    /** 退出状态 */
    int EXIT = 2;
}
