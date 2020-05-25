package com.game.gameserver.module.player.entity;

import com.game.gameserver.dictionary.dict.DictRoleLevelProperty;
import com.game.gameserver.module.account.model.Account;
import com.game.gameserver.module.player.model.Role;
import io.netty.channel.Channel;

/**
 * 玩家角色实体
 * @author xuewenkang
 * @date 2020/5/25 0:10
 */
public class PlayerEntity {
    /** 角色基本属性 */
    Role role;

    /** 基础等级属性 */
    private DictRoleLevelProperty roleLevelProperty;

    /** account */
    private Account account;

    /** channel */
    private Channel channel;
}
