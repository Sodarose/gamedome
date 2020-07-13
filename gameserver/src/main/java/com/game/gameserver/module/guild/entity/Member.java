package com.game.gameserver.module.union.entity;

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
}
