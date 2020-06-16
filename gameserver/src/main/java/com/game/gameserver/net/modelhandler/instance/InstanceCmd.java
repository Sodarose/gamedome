package com.game.gameserver.net.modelhandler.instance;

/**
 * 副本命令
 *
 * @author xuewenkang
 * @date 2020/6/8 19:20
 */
public interface InstanceCmd {
    /** 查看副本列表 */
    short INSTANCE_LIST = 1001;
    /** 进入副本 */
    short ENTRY_INSTANCE = 1002;
    /** 退出副本 */
    short EXIT_INSTANCE = 1003;
    /** */

}
