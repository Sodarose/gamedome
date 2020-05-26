package com.game.module.player;

/**
 * @author xuewenkang
 * @date 2020/5/25 14:06
 */
public interface PlayerCmd {
    short LIST_ROLES = 1000;
    /** 登录角色 */
    short LOGIN_ROLE = 1001;
    /** 请求角色信息 */
    short PLAYER_INFO = 1002;
}
