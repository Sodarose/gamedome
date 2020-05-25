package com.game.gameserver.dictionary.dict;

import lombok.Data;

/**
 * 角色-职业-等级属性
 * @author xuewenkang
 * @date 2020/5/21 16:10
 */
@Data
public class DictRoleLevelProperty {
    private Integer careerId;
    private Integer level;
    private Integer hp;
    private Integer mp;
    private Integer power;
    private Integer brains;
    private Integer strength;
    private Integer spirit;
    private Integer phyAttack;
    private Integer magicAttack;
    private Integer phyDefense;
    private Integer magicDefense;
    private Double attackSpeed;
    private Double moveSpeed;
}
