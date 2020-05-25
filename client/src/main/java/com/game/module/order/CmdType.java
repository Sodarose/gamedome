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
    /** 产看角色信息 */
    String SELF_MESSAGE = "SELF";
}
