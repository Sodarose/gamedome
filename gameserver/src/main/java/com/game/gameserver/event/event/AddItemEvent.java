package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * 道具增加事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:44
 */
@Data
public class AddItemEvent implements Event {
    private Player player;
    private Item item;

    public AddItemEvent(Player player, Item item) {
        this.player = player;
        this.item = item;
    }
}
