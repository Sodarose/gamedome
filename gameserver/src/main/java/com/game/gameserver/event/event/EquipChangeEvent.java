package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

import java.awt.*;

/**
 * 装备变更事件
 *
 * @author xuewenkang
 * @date 2020/7/15 10:42
 */
@Data
public class EquipChangeEvent implements Event {
    private Player player;

    public EquipChangeEvent(Player player) {
        this.player = player;
    }
}
