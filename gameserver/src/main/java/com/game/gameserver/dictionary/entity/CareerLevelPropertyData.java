package com.game.gameserver.dictionary.entity;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/2 20:26
 */
@Data
public class CareerLevelPropertyData {
    private int id;
    private int careerId;
    private int level;
    private PropertyData propertyData;
}