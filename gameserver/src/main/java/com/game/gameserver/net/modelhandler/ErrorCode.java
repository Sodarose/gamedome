package com.game.gameserver.net.modelhandler;

/**
 * @author xuewenkang
 * @date 2020/5/29 15:10
 */
public interface ErrorCode {
    short MODEL_KEY = 0;

    /** 打开背包错误 */
    short OPEN_ERROR = 1;
    /** 角色未登录 */
    short PLAYER_NOT_LOGIN = 2;
}
