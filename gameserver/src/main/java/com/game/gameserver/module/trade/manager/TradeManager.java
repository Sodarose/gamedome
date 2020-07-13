package com.game.gameserver.module.trade.manager;

import com.game.gameserver.module.trade.model.TradeBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/7/13 6:35
 */
@Component
public class TradeManager {
    private static Logger logger = LoggerFactory.getLogger(TradeManager.class);

    /** 交易信息缓存 */
    public final static Map<Long,TradeBoard> LONG_TRADE_BOARD_MAP = new ConcurrentHashMap<>();

    public void putTradeBoard(long id,TradeBoard tradeBoard){
        LONG_TRADE_BOARD_MAP.put(id,tradeBoard);
    }

    public void removeTradeBoard(long id){
        LONG_TRADE_BOARD_MAP.remove(id);
    }

    public TradeBoard getTradeBoard(long id){
        return LONG_TRADE_BOARD_MAP.get(id);
    }
}
