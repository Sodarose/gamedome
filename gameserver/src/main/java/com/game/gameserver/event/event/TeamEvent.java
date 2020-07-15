package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.team.model.Team;
import lombok.Data;

/**
 * 团队事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:46
 */
@Data
public class TeamEvent implements Event {
    private Player player;
    private Team team;

    public TeamEvent(Player player,Team team){
        this.player = player;
        this.team = team;
    }
}
