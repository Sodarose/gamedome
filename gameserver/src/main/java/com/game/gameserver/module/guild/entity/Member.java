package com.game.gameserver.module.guild.entity;

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
    /**
     * 唯一Id
     */
    private long id;
    /**
     * 玩家名称
     */
    private String name;
    /**
     * 职位
     */
    private Integer position;
    /**
     * 玩家ID
     */
    private Long playerId;

    public static Member valueOf(String name, int position, long playerId) {
        Member member = new Member();
        member.setId(GameUUID.getInstance().generate());
        member.setName(name);
        member.setPosition(position);
        member.setPlayerId(playerId);
        return member;
    }
}
