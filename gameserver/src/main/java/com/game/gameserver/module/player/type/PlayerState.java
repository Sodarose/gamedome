package com.game.gameserver.module.player.type;

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

    /** 战士 行为状态 */
    int STANDBY = 1001;
    int ATTACK = 1002;
    int TAKEOFF = 1003;


    /** 奶妈 行为状态 */
    /** 治疗 */
    int CURL = 1004;

    /** */

}
