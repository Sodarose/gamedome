package com.game.gameserver.net.modelhandler.account;

/**
 * @author xuewenkang
 * @date 2020/5/24 15:53
 */
public interface AccountCmd {
    short LOGIN = 0;
    short LOGOUT = 1;
    short REGISTER = 2;
    short LOGIN_RES = 3;
    short REGISTER_RES = 4;
}
