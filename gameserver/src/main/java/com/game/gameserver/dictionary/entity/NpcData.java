package com.game.gameserver.dictionary.entity;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/2 20:20
 */
@Data
public class NpcData {
    private int id;
    private String name;
    private int level;
    private PropertyData propertyData;
    private String talk;
}
