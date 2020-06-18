package com.game.gameserver.net.modelhandler.goods;

/**
 * @author xuewenkang
 * @date 2020/5/27 14:37
 */
public interface GoodsCmd {
    /** 查看道具 */
    short CHECK_ITEM = 1001;
    /** 使用道具 */
    short USE_ITEM = 1002;
    /** 丢弃道具 */
    short DISCARD_ITEM = 1003;
    /** 移动道具 */
    short MOVE_ITEM = 1004;
    /** 添加道具 */
    short ADD_ITEM = 1005;
    /** 错误表示 */
    short ERROR = 1006;
    /** 搜索道具 */
    short SEARCH_ITEM = 1007;
}
