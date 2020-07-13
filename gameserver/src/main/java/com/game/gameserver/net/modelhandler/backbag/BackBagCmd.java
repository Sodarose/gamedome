package com.game.gameserver.net.modelhandler.backbag;

/**
 * @author xuewenkang
 * @date 2020/7/12 13:54
 */
public interface BackBagCmd {
    int SHOW_BACK_BAG = 1001;
    /** 移动道具 */
    int MOVE_ITEM = 1002;
    /** 丢弃道具 */
    int DISCARD_ITEM = 1003;
    /** */
    int SYNC = 1004;
}
