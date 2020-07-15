package com.game.gameserver.event.event;

import com.game.gameserver.event.Event;
import com.game.gameserver.module.guild.entity.GuildEntity;
import com.game.gameserver.module.guild.model.Guild;
import com.game.gameserver.module.player.model.Player;
import lombok.Data;

/**
 * 公会事件
 *
 * @author xuewenkang
 * @date 2020/7/15 12:47
 */
@Data
public class GuildEvent implements Event {
    private Player player;
    private Guild guild;

    public GuildEvent(Player player, Guild guild) {
        this.player = player;
        this.guild = guild;
    }
}
