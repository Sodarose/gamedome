package com.game.gameserver.game.player;

import com.game.gameserver.game.account.Account;
import com.game.protocol.MessageType;
import com.game.protocol.Protocol;
import com.game.util.MessageUtil;
import io.netty.channel.Channel;
import lombok.Data;


/**
 * 游戏玩家
 * @author xuewenkang
 * @date 2020/5/18 14:04
 */
@Data
public class Player {

    /**
     * ID
     */
    private Integer id;
    
    /**
     * 角色姓名
     */
    private String name;

    /**
     * 账号
     */
    private Account account;

    /**
     * channel
     */
    private Channel channel;

    /**
     * 当前角色所在的场景ID
     */
    private Integer worldId;
}
