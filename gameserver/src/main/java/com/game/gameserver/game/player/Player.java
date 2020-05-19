package com.game.gameserver.game.player;

import com.game.gameserver.game.account.Account;
import io.netty.channel.Channel;
import lombok.Data;


/**
 * 游戏玩家
 * @author xuewenkang
 * @date 2020/5/18 14:04
 */
@Data
public class Player {

    private Integer id;

    private String name;

    /**
     * 账号
     */
    private Account account;

    /**
     * channel
     */
    private Channel channel;


}
