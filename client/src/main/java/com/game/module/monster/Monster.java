package com.game.module.monster;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/1 15:17
 */
@Data
public class Monster {
    private long id;
    private String name;
    private int level;
    private int state;
    private int hp;
    private int currHp;
    private int mp;
    private int currMp;
    private int attack;
    private int defense;

    public Monster() {
    }

}
