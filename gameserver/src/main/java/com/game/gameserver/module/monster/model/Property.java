package com.game.gameserver.module.monster.model;

import lombok.Data;

/**
 * 怪物与玩家共同的属性类
 *
 * @author xuewenkang
 * @date 2020/6/9 11:46
 */
@Data
public class Property {
    private int hp;
    private int currHp;
    private int mp;
    private int currMp;
    private int attack;
    private int defense;

    public Property() {

    }

    public Property(int hp, int mp, int attack, int defense) {
        this.hp = hp;
        this.currHp = hp;
        this.mp = mp;
        this.currMp = mp;
        this.attack = attack;
        this.defense = defense;
    }
}
