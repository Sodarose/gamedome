package com.game.gameserver.net.modelhandler.item;

/**
 * @author xuewenkang
 * @date 2020/5/27 14:37
 */
public interface ItemCmd {
    /** 获取装备栏 */
    short EQUIP_BAG_REQ = 1001;
    /** 获取角色背包 */
    short PLAYER_BAG_REQ = 1002;
    /** 获取角色仓库 */
    short PLAYER_WAREHOUSE_REQ = 1003;
    /** 使用道具 */
    short USE_ITEM = 1002;
    /** 丢弃道具 */
    short DROP_ITEM = 1003;
    /** 移动道具 */
    short MOVE_ITEM = 1004;
    /** 背包整理 */
    short CLEAR_BAG = 1006;
    /** 卸下装备*/
    short TAKEOFF_EQUIP = 1007;
    /** 穿上装备*/
    short PUT_ON_EQUIP = 1008;
    /** 修理装备*/
    short FIX_EQUIP = 1009;
}
