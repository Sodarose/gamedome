package com.game.gameserver.module.user.entity;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/2 9:45
 */
@Data
public class Role {
    /** id */
    private Long id;
    /** 姓名 */
    private String name;
    /** 等级 */
    private Integer level;
    /** 职业*/
    private Integer careerId;
}
