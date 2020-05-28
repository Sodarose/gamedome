package com.game.gameserver.net.modelhandler.bag;

/**
 * 背包命令
 * @author xuewenkang
 * @date 2020/5/26 21:58
 */
public interface BagCmd {
    /** 打开背包 */
    short OPEN_BAG = 1000;
    /** 关闭背包 */
    short CLOSE_BAG = 1001;
}
