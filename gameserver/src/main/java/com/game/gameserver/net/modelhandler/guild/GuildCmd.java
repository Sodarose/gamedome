package com.game.gameserver.net.modelhandler.guild;

/**
 * @author xuewenkang
 * @date 2020/7/8 15:38
 */
public interface GuildCmd {
    /** 创建公会 */
    int CREATE_GUILD = 1001;
    /** 公会列表 */
    int SHOW_GUILD_LIST = 1002;
    /** 查看公会 */
    int SHOW_GUILD = 1003;
    /** 申请加入公会*/
    int APPLY_FOR_GUILD = 1004;
    /** 处理用户申请 */
    int PROCESS_Guild_APPLY = 1005;
    /** 授予职位 */
    int APPOINT = 1006;
    /** 捐献金币*/
    int DONATE_GOLDS = 1007;
    /** 展示公会仓库*/
    int SHOW_GUILD_W =1008;
    /** 放入公会仓库 */
    int PUTIN_GUILD_W = 1009;
    /** 从公会仓库取出*/
    int TAKEOUT_GUILD_W = 1010;
    /** 整理仓库*/
    int CLEAR_UP_W = 1011;
    /** 退出公会 */
    int EXIT_GUILD = 1012;
}
