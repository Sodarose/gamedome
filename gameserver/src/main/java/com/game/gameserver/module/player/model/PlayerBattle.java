package com.game.gameserver.module.player.model;

import lombok.Data;

/**
 * 玩家战斗属性
 *
 * @author xuewenkang
 * @date 2020/7/10 1:12
 */
@Data
public class PlayerBattle {
    /** 战斗属性 */
    private int currHp = 10000;
    private int currMp = 10000;

    private int hp = 10000;
    private int mp = 10000;
    private int attack = 100;
    private int defense = 1500;
}
