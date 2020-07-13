package com.game.gameserver.net.modelhandler.trade;

/**
 * @author xuewenkang
 * @date 2020/7/13 9:32
 */
public interface TradeCmd {
    /** 展示当前交易信息 */
    int SHOW_TRADE = 1001;
    /**  申请交易*/
    int INITIATE = 1002;
    /** 回复交易*/
    int REPLY_TRADE = 1003;
    /** 放入道具*/
    int PUT_ITEM_TRADE = 1004;
    /** 放入金币*/
    int PUT_GOLD_TRADE = 1005;
    /** 确认当前交易*/
    int AFFIRM_TRADE = 1006;
    /** 取消交易 */
    int CANCEL_TRADE = 1007;
}
