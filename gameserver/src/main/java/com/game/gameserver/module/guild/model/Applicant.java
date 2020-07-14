package com.game.gameserver.module.guild.model;

import lombok.Data;

/**
 * 申请者
 *
 * @author xuewenkang
 * @date 2020/7/3 12:21
 */
@Data
public class Applicant {

    /** 申请者名称 */
    private String name;

    /** 申请者id */
    private Long playerId;

    public Applicant(String name, long playerId) {
        this.name = name;
        this.playerId = playerId;
    }
}
