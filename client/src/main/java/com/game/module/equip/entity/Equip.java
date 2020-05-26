package com.game.module.equip.entity;

import com.game.module.player.model.Property;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/26 15:39
 */
@Data
public class Equip {
    private int id;
    private String name;
    private int level;
    private int quality;
    private int part;
    private int durability;
    private int maxDurability;
    private Integer hp;
    private Integer mp;
    private Integer phyAttack;
    private Integer magicAttack;
    private Integer phyDefense;
    private Integer magicDefense;
    private Double attackSpeed;
    private Double moveSpeed;
}
