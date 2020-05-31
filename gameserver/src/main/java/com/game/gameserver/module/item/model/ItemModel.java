package com.game.gameserver.module.item.model;

import lombok.Data;

/**
 * 对应物品数据库
 * */
@Data
public class ItemModel {
    private Integer id;
    private Integer itemId;
    private Integer playerId;
    private Integer count;
    private Integer place;
    private Integer bagIndex;
}
