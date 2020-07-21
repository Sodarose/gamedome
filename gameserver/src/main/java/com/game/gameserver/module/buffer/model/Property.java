package com.game.gameserver.module.buffer.model;

import lombok.Data;

/**
 * 固定属性加成
 *
 * @author xuewenkang
 * @date 2020/7/16 10:11
 */
@Data
public class Property {
    /** 战斗属性 */
    private int maxHp;
    private int maxMp;
    private int attack;
    private int defense;
}
