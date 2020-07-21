package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;


/**
 * 怪物死亡事件
>>>>>>> dev
 *
 * @author xuewenkang
 * @date 2020/7/15 12:45
 */
@Data
public class MonsterDeadEvent implements Event {
    private Player player;
    private Monster monster;

    public MonsterDeadEvent(Player player,Monster monster){
        this.player = player;
        this.monster = monster;
    }
}
