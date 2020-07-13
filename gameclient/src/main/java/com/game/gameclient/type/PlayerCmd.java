package com.game.gameclient.type;

/**
 * @author xuewenkang
 * @date 2020/5/25 12:21
 */
public interface PlayerCmd {
    int LOGIN = 1001;
    int CREATE = 1002;
    int ROLE_LIST = 1003;
    int CAREER_LIST = 1004;
    int SHOW_PLAYER = 1005;
    int LOGOUT = 1006;
    /** 同步数据 */
    int SYNC = 1007;
}
