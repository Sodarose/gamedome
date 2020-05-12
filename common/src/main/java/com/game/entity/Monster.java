package com.game.entity;


import lombok.Data;

/**
 * @author xuwenkang
 * 游戏怪物
 */
@Data
public class Monster {
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 血量
     * */
    private Integer ph;

    /**
     * 蓝条
     * */
    private Integer mp;

    /**
     * 物理攻击
     * */
    private Integer phyAttack;

    /**
     * 魔法攻击力
     * */
    private Integer magicAttack;

    /**
     * 物理防御
     * */
    private Integer phyDefense;

    /**
     * 魔法防御
     * */
    private Integer magicDefense;

    /**
     * 所在地图ID 也是所在场景ID
     * */
    private Integer mapId;

    /**
     * 角色状态
     * */
    private Integer status;
}
