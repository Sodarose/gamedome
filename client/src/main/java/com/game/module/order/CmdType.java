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
    String CONFIRM_ROLE = "CONFIRM_ROLE";
    /** 查看当前场景信息 */
    String SHOW_SCENE = "SHOW_SCENE";
    /** 产看角色信息 */
    String SELF_MESSAGE = "SELF";
    /** 打开背包 */
    String OPEN_BAG = "OPEN_BAG";
    /** 打开装备栏 */
    String OPEN_EQUIP = "OPEN_EQUIP_BAR";
    /** 关闭背包*/
    String CLOSE_BAG = "CLOSE_BAG";
    /** 使用后道具 */
    String USER_ITEM = "USE_ITEM";
}
