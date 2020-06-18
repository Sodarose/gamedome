package com.game.gameserver.net.modelhandler.team;

/**
 * @author xuewenkang
 * @date 2020/6/17 18:26
 */
public interface TeamCmd {
    /** 创建队伍 */
    short CREATE_TEAM = 1001;
    /** 解散队伍 */
    short DISSOLVE_TEAM = 1002;
    /** 解散通知 */
    short DISSOLVE_TEAM_NOTIFY = 1003;
    /** 进入队伍 */
    short ENTRY_TEAM = 1004;
    /** 退出队伍 */
    short EXIT_TEAM = 1005;
    /** 退出队伍通知 */
    short EXIT_TEAM_NOTIFY = 1006;
    /** 踢出队伍 */
    short KICK_TEAM = 1006;
    /** 队伍列表 */
    short TEAM_LIST = 1007;
    /** 踢出队伍通知 */
    short KICK_TEAM_NOTIFY = 1008;
    /** 同步组队信息*/
    short SYNC_TEAM = 1009;
}
