package com.game.module.order;

/**
 * @author xuewenkang
 * @date 2020/5/25 12:07
 */
public interface CmdType {
    String CMD_CLEAN = "CLEAN";
    /** 列出当前 角色列表*/
    String LIST_ROLES = "LIST_ROLES";
    /** 选中角色 */
    String LOGIN_ROLE = "LOGIN_ROLE";
    /** 产看角色信息 */
    String SHOW_ME = "SHOW_ME";
    /** 查看当前场景信息 */
    String SHOW_SCENE = "SHOW_SCENE";
    /** 打开背包 */
    String SHOW_BAG = "SHOW_BAG";
    /** 打开装备栏 */
    String SHOW_EQUIP_BAR = "SHOW_EQUIP_BAR";
    /** 使用物品 */
    String USER_GOODS = "USER_GOODS";
    /** 显示商店 */
    String SHOW_STORE = "SHOW_STORE";
    /** 购买 */
    String BUY = "BUY";
    /** 出售 */
    String SELL = "SELL";
    /** 私聊 */
    String SEND_P_CHAT = "SEND_P_CHAT";
    /** 频道 */
    String SEND_C_CHAT = "SEND_C_CHAT";
    /** 本地聊天 */
    String SEND_L_CHAT = "SEND_L_CHAT";
}
