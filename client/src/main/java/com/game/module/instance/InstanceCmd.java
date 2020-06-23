package com.game.module.instance;

/**
 * @author xuewenkang
 * @date 2020/6/22 11:18
 */
public interface InstanceCmd {
    /** 查看副本列表 */
    short INSTANCE_LIST = 1001;
    /** 进入副本 */
    short ENTRY_INSTANCE = 1002;
    /** 组队进入副本*/
    short ENTRY_INSTANCE_BY_TEAM = 1003;
    /** 退出副本 */
    short EXIT_INSTANCE = 1004;
    /** 副本通关成功*/
    short INSTANCE_SUCCESS = 1005;
    /** 同步副本数据*/
    short SYNC_INSTANCE_INFO = 1006;
}
