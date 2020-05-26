package com.game.gameserver.module.player.facade;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerModel;
import com.game.protocol.PlayerProtocol;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/25 0:09
 */
public interface PlayerFacade {
    final AttributeKey<Player> PLAYER_ENTITY_ATTRIBUTE_KEY = AttributeKey.newInstance("PLAYER_ENTITY_ATTRIBUTE_KEY");
    List<PlayerModel> getPlayListByAccountId(Integer accountId);
    PlayerProtocol.PlayerInfo loginRoleByRoleId(Integer roleId, Channel channel);
}
