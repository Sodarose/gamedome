package com.game.gameserver.module.task.type;

/**
 * 角色行为事件(暂时与事件未合并)
 *
 * @author xuewenkang
 * @date 2020/6/29 15:29
 */
public interface CondType {
    /** 杀敌*/
    int KILL = 0;
    /** 升级*/
    int LEVEL_UP = 1;
    /** 对话 */
    int DIALOGUE = 3;
    /** 极品装备*/
    int LEGEND_EQUIPS = 4;
    /** 副本*/
    int INSTANCE = 5;
    /** 装备等级*/
    int EQUIP_LEVEL = 6;
    /** 添加一个好友*/
    int ADD_FRIEND = 7;
    /** 组队*/
    int ENTRY_TEAM = 8;
    /** 加入工会*/
    int ENTRY_UNION = 9;
    /** 第一次交易*/
    int FIRST_TRADE = 10;
    /** */
}
