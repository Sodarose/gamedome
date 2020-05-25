package com.game.gameserver.dictionary.dict;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/21 15:54
 */
@Data
public class DictMonster {
    private Integer id;
    private String name;
    private Integer type;
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
    private Integer awardId;
    private String description;
}
