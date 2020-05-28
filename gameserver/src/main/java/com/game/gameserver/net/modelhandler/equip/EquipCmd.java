package com.game.gameserver.net.modelhandler.equip;

/**
 * 装备命令
 * @author xuewenkang
 * @date 2020/5/26 21:16
 */
public interface EquipCmd {
    /** 出现错误 */
    short EQUIP_ERROR = 0;
    /** 查看装备 */
    short CHECK_EQUIP = 1000;
    /** 卸下装备 */
    short TAKE_EQUIP = 1001;
    /** 穿上装备 */
    short PUT_EQUIP = 1002;
    /** 搜索装备 */
    short SEARCH_EQUIP = 1003;
}
