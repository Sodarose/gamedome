package com.game.gameserver.module.trade.model;

import com.game.gameserver.module.item.model.Item;
import lombok.Data;

import java.util.List;

/**
 * 交易栏
 *
 * @author xuewenkang
 * @date 2020/7/7 9:41
 */
@Data
public class TradeBox {
    /** 玩家id */
    private long playerId;
    /** 道具列表*/
    private List<Item> itemList;
    /** 金币*/
    private int golds;
    /** 是否确认 */
    private boolean affirm;
}
