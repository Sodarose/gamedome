package com.game.gameserver.module.task.type;

/**
 * 任务单元类型
 *
 * @author xuewenkang
 * @date 2020/7/15 11:20
 */
public enum  TaskType {
    /** 杀怪 */
    KILL_MONSTER(1),
    /** 升级 */
    LEVEL_UP(2),
    /** NPC交流 */
    TALK_NPC(3),
    /** 收集装备 */
    COLLECT_BEST_EQUIP(4),
    /** 副本 */
    INSTANCE(5),
    /** 装备变更 */
    EQUIPMENT_CHANGE(6),
    /** 加好友 */
    FRIEND(7),
    /** 团队 */
    TEAM(8),
    /** 交易 */
    TREAD(9),
    /** PK*/
    PK(10),
    /** 金币 */
    GOLDS(11),
    /** 任务完成 */
    COMPLETE_TASK(12),
    /** */
    GUILD(13);

    private int type;

    TaskType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
