package com.game.gameserver.module.trade.model;

import com.game.gameserver.module.item.model.Item;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易栏
 *
 * @author xuewenkang
 * @date 2020/7/7 9:41
 */
@Data
public class TradeBar {
    /** 道具列表*/
    private List<Item> itemList;
    /** 金币*/
    private int golds;
    /** 是否确认 */
    private boolean affirm;

    public TradeBar(){
        this.itemList = new ArrayList<>();
        this.golds = 0;
        this.affirm = false;
    }
}
