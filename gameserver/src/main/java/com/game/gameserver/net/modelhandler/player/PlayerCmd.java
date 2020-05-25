package com.game.gameserver.net.modelhandler.player;

/**
 * @author xuewenkang
 * @date 2020/5/25 12:21
 */
public interface PlayerCmd {
    /** 请求角色列表 */
    short LIST_ROLES = 1000;
    /** 登录角色 */
    short LOGIN_ROLE = 1001;
    /** 返回角色信息 */
    short PLAYER_INFO = 1002;
}