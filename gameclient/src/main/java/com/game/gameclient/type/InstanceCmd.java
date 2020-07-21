package com.game.gameclient.type;

/**
 * @author xuewenkang
 * @date 2020/6/22 11:18
 */
public interface InstanceCmd {
    /** 查看副本列表 */
    int SHOW_ALL_INSTANCE = 1001;
    /** 进入副本 */
    int ENTRY_INSTANCE = 1002;
    /** 组队进入副本*/
    int ENTRY_INSTANCE_BY_TEAM = 1003;
    /** 退出副本 */
    int EXIT_INSTANCE = 1004;
    /** 同步副本数据*/
    int SYNC = 1005;
}
