package com.game.gameserver.module.player.entity;

import lombok.Data;

/**
 * 玩家角色战斗属性
 *
 * @author xuewenkang
 * @date 2020/6/10 10:22
 */
@Data
public class PlayerBattle {
    private int hp = 1000;
    private int currHp = 1000;
    private int mp = 1000;
    private int currMp = 1000;
    private int attack = 1000;
    private int defense = 1000;
    private int attackSpeed = 2;

    public void subtractCurrHp(int value) {
        currHp -= value;
        if (currHp <= 0) {
            currHp = 0;
        }
    }

    public void addCurrHp(int value) {
        currHp += value;
        if (currHp > hp) {
            currHp = hp;
        }
    }

    public void addCurrMp(int value) {
        currMp += value;
        if (currMp > mp) {
            currMp = mp;
        }
    }

    public void subtractCurrMap(int value) {
        currMp -= value;
        if (currMp < 0) {
            currMp = 0;
        }
    }

    public void addHp(int value) {
        hp += value;
    }

    public void addMp(int value) {
        mp += value;
    }

    public void subtractHp(int value) {
        hp -= value;
    }

    public void subtractMp(int value) {
        mp -= value;
    }

    public void addAttack(int value) {
        attack += value;
    }

    public void subtractAttack(int value) {
        attack -= value;
    }

    public void addDefense(int value) {
        defense += value;
    }

    public void subtractDefense(int value) {
        defense -= value;
    }

    public boolean isCurrHpEmpty() {
        return currHp == 0;
    }
}
