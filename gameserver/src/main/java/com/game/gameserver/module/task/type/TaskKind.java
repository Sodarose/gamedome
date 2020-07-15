package com.game.gameserver.module.task.type;

/**
 * 任务种类
 *
 * @author xuewenkang
 * @date 2020/6/29 15:29
 */
public interface TaskKind {
    /** 主线 */
    int MAIN = 1;

    /** 支线 */
    int BRANCH = 2;

    /** 副本任务 */
    int INSTANCE = 3;

    /** 每日任务 */
    int DAILY = 4;
}
