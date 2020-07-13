package com.game.gameserver.module.trade.state;

/**
 * @author xuewenkang
 * @date 2020/7/13 6:42
 */
public interface TradeState {
    /** 等待 */
    int WAIT = 0;
    /** 进行 */
    int RUNNING = 1;
    /** 结束 */
    int END = 2;
}
