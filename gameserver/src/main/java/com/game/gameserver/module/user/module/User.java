package com.game.gameserver.module.user.module;

import com.game.gameserver.module.user.entity.AccountEntity;
import com.game.gameserver.module.user.entity.UserEntity;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/11 1:43
 */
@Data
public class Account extends UserEntity {
    /** 账户连接信息 */
    private Channel channel;
}
