package com.game.gameserver.module.item.model;

import lombok.Data;

import java.sql.Blob;

/**
 * 对应物品数据库
 */
@Data
public class ItemModel {
    private Integer id;
    private Integer itemId;
    private Integer itemType;
    private Integer playerId;
    private Integer place;
    private String  property;
}
