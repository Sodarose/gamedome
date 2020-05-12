package com.game.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuewenkang
 * 游戏角色
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
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
     * 所属用户ID
     * */
    private Integer userId;

}
