package com.game.gameserver.module.guild.model;

import com.game.gameserver.util.GameUUID;
import lombok.Data;

/**
 * 工会成员实体
 *
 * @author xuewenkang
 * @date 2020/7/2 20:49
 */
@Data
public class Member {

    /** 玩家名称 */
    private String name;

    /** 职位 */
    private Integer position;

    /** 玩家ID */
    private Long playerId;

    public Member(){

    }

    public Member(String name, int position, long playerId) {
        this.name = name;
        this.position = position;
        this.playerId = playerId;
    }
}
