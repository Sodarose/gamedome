package com.game.gameserver.dictionary.dict;

import lombok.Data;

/**
 * 装备表
 * @author xuewenkang
 * @date 2020/5/21 15:44
 */
@Data
public class DictEquip {
    private Integer id;
    private Integer part;
    private Integer maxDurability;
    private Integer hp;
    private Integer mp;
    private Integer phyAttack;
    private Integer magicAttack;
    private Integer phyDefense;
    private Integer magicDefense;
    private Double attackSpeed;
    private Double moveSpeed;
}
