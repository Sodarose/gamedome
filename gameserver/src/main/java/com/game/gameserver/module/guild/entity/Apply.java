package com.game.gameserver.module.guild.entity;

import lombok.Data;

/**
 * 申请者
 *
 * @author xuewenkang
 * @date 2020/7/3 12:21
 */
@Data
public class Apply {
    /**
     * 申请者名称
     */
    private String name;
    /**
     * 申请者id
     */
    private Long playerId;

    public Apply(String name, long playerId) {
        this.name = name;
        this.playerId = playerId;
    }
}
