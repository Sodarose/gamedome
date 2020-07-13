package com.game.gameserver.module.user.module;

import com.game.gameserver.module.player.entity.Role;
import com.game.gameserver.module.user.entity.UserEntity;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/11 1:43
 */
@Data
public class User extends UserEntity {
    /** 账户连接信息 */
    private Channel channel;
    /** 玩家角色列表 */
    private List<Long> roles = new ArrayList<>();
}
