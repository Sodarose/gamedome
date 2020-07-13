package com.game.gameserver.module.guild.type;

/**
 * 公会权限
 *
 * @author xuewenkang
 * @date 2020/7/3 10:50
 */
public interface UnionPermission {
    /** 职位任命 */
    int APPOINT = 1;
    /** 修改公会公告 */
    int ANNOUNCEMENT = 2;
    /** 处理申请人信息*/
    int PROCESS_APPLY = 3;
    /** 踢人*/
    int KICK = 4;
    /** 公会升级 */
    int LEVEL_UP = 5;
    /** 解散公会 */
    int DISSOLVE = 6;
    /** 使用公会仓库*/
    int USE_WAREHOUSE = 7;
}
