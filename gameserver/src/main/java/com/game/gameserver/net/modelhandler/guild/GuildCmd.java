package com.game.gameserver.net.modelhandler.guild;

/**
 * @author xuewenkang
 * @date 2020/7/8 15:38
 */
public interface GuildCmd {
    /** 创建公会 */
    short CREATE_GUILD = 1001;
    /** 公会列表 */
    short GUILD_LIST = 1002;
    /** 查看公会 */
    short CHECK_GUILD = 1003;
    /** 申请加入公会*/
    short APPLY_GUILD = 1004;
    /** 处理用户申请 */
    short PROCESS_APPLY = 1005;
    /** 授予职位 */
    short APPOINT = 1006;
    /** 捐献金币*/
    short DONATE_GOLDS = 1007;
    /** 展示公会仓库*/
    short SHOW_GUILD_W =1008;
    /** 放入公会仓库 */
    short PUT_GUILD_W = 1009;
    /** 从公会仓库取出*/
    short TAKE_GUILD_W = 10010;
}