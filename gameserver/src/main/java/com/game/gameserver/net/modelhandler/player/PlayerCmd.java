package com.game.gameserver.net.modelhandler.player;

/**
 * @author xuewenkang
 * @date 2020/5/25 12:21
 */
public interface PlayerCmd {
    /** 请求角色列表 */
    short QUERY_ROLE_LIST = 1000;
    /** 登录角色请求 */
    short LOGIN_ROLE = 1001;
    /** 角色数据请求 */
    short PLAYER_INFO_REQ = 1003;
    /** 角色信息同步 */
    short SYNC_PLAYER_INFO = 1002;
    /** 战斗属性同步 */
    short SYNC_PLAYER_BATTLE = 1003;
}
