package com.game.module.player;

import lombok.Data;

/**
 *     int32 hp = 1;
 *     int32 currHp = 2;
 *     int32 mp = 3;
 *     int32 currMp = 4;
 *     int32 attack = 5;
 *     int32 defense = 6;
 * @author xuewenkang
 * @date 2020/6/15 10:40
 */
@Data
public class PlayerBattle {
    private int hp;
    private int currHp;
    private int mp;
    private int currMp;
    private int attack;
    private int defense;
}
