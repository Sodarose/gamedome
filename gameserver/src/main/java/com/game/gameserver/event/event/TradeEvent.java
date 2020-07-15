package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 *
 * 交易事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:47
 */
@Data
public class TradeEvent implements Event {
    private Player player;

    public TradeEvent(Player player){
        this.player = player;
    }
}
