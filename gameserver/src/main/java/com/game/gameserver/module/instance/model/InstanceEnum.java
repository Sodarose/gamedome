package com.game.gameserver.module.instance.model;

/**
 * 副本状态
 *
 * @author xuewenkang
 * @date 2020/6/16 15:53
 */
public enum InstanceEnum {
    /** 副本运行状态 */
    RUNNING,
    /** 副本通关状态  */
    OVER,
    /** 副本失败状态 */
    FAILED,
    /** 副本结束状态 */
    END;
}
