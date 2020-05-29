package com.game.gameserver.module.item.model;

import lombok.Data;

/**
 * 道具表  对应数据库表
 * @author xuewenkang
 * @date 2020/5/25 17:41
 */
@Data
public class ItemModel {
    private Integer id;
    /** 对应基础静态数据 */
    private Integer itemId;
    private Integer itemType;
    private Integer roleId;
    private Integer bagId;
    private Integer bagIndex;
    private Integer itemCount;
}
