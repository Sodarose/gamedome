package com.game.gameserver.dictionary.entity;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/2 20:22
 */
@Data
public class MonsterData {
    private int id;
    private String name;
    private int level;
    private PropertyData propertyData;

}
