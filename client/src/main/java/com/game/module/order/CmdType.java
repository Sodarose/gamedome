package com.game.module.order;

/**
 * @author xuewenkang
 * @date 2020/5/25 12:07
 */
public interface CmdType {
    String CMD_CLEAN = "CLEAN";
    // 角色
    /** 列出当前 角色列表*/
    String LIST_ROLES = "LIST_ROLES";
    /** 选中角色 */
    String LOGIN_ROLE = "LOGIN_ROLE";
    /** 产看角色信息 */
    String SHOW_ME = "SHOW_ME";


    // 场景
    /** 查看当前场景信息 */
    String SHOW_SCENE = "SHOW_SCENE";
    /**  scene AIO */
    String SCENE_AIO = "SCENE_AIO";

    // 背包
    /** 打开背包 */
    String SHOW_BAG = "SHOW_BAG";
    /** 打开装备栏 */
    String SHOW_EQUIP_BAR = "SHOW_EQUIP_BAR";
    /** 使用物品 */
    String USER_GOODS = "USER_GOODS";

    // 商店
    /** 显示商店 */
    String SHOW_STORE = "SHOW_STORE";
    /** 购买 */
    String BUY = "BUY";
    /** 出售 */
    String SELL = "SELL";

    // 聊天
    /** 私聊 */
    String SEND_P_CHAT = "SEND_P_CHAT";
    /** 频道 */
    String SEND_C_CHAT = "SEND_C_CHAT";
    /** 本地聊天 */
    String SEND_L_CHAT = "SEND_L_CHAT";

    // 副本
    /** 展示当前副本信息*/
    String SHOW_INSTANCE_LIST = "SHOW_INSTANCE_LIST";
    /** 进入副本*/
    String ENTRY_INSTANCE = "ENTRY_INSTANCE";
    /** 组队进入副本*/
    String ENTRY_INSTANCE_BY_TEAM = "ENTRY_INSTANCE_BY_TEAM";
    /** 退出副本*/
    String EXIT_INSTANCE = "EXIT_INSTANCE";
    /** 带着团队 退出副本*/
    String EXIT_INSTANCE_BY_TEAM = "EXIT_INSTANCE_BY_TEAM";
    /** instance AIO */
    String INSTANCE_AIO = "INSTANCE_AIO";

    // 队伍
    /**  创建队伍*/
    String CREATE_TEAM = "CREATE_TEAM";
    /** 展示队伍*/
    String SHOW_TEAM = "SHOW_TEAM";
    /** 列出队伍列表*/
    String TEAM_LIST = "TEAM_LIST";
    /** 加入队伍*/
    String ENTRY_TEAM = "ENTRY_TEAM";
    /** 退出队伍*/
    String EXIT_TEAM = "EXIT_TEAM";

    // 邮箱
    /** 打开邮箱 */
    String OPEN_EMAIL_BOS = "OPEN_EMAIL_BOS";
    /** 邮件列表*/
    String EMAIL_LIST = "EMAIL_LIST";
    /** 发送邮件 */
    String SEND_EMAIL = "SEND_EMAIL";

    // 战斗
    /** 攻击 */
    String ATTACK = "ATTACK";
    /** 切换战斗模式 */
    String CHANGE_FIGHTER_MODULE = "CHANGE_FIGHTER_MODULE";
    /** 使用技能*/
    String USE_SKILL = "USE_SKILL";

    // 任务
    /** 查看任务列表 */
    String QUERY_ALL_TASK = "QUERY_ALL_TASK";
    /** 查询可接受的任务 */
    String QUERY_RECEIVE_ABLE_TASK = "QUERY_RECEIVE_ABLE_TASK";
    /** 查看自己的任务列表 */
    String QUERY_RECEIVE_TASK = "QUERY_RECEIVE_TASK";
    /** 提交任务 */
    String SUBMIT_TASK = "SUBMIT_TASK";
    /** 接受任务*/
    String ACCEPT_TASK = "ACCEPT_TASK";
    /** 取消任务 */
    String CANCEL_TASK = "CANCEL_TASK";
}
