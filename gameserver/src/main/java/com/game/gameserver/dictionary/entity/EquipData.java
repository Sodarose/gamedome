package com.game.gameserver.dictionary.entity;

import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/2 20:25
 */
@Data
public class EquipData {
    private int id;
    private int itemId;
    private int part;
    private int maxDurability;
    private PropertyData propertyData;
}
