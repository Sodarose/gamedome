package com.game.gameserver.module.player.facade;

import com.game.gameserver.module.player.model.Role;
import com.game.gameserver.module.player.vo.PlayerVo;
import com.game.protocol.Message;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/25 0:09
 */
public interface PlayerFacade {
    List<Role> getPlayListByAccountId(Integer accountId);
    PlayerVo loginRoleByRoleId(Integer roleId, Channel channel);
}
