package com.game.gameserver.module.item.entity;

import lombok.Data;

/**
 * 物品基础父类
 *
 * @author xuewenkang
 * @date 2020/6/11 14:24
 */
@Data
public class Item {
    /** 物品唯一Id */
    protected Long id;
    /** 道具类型  */
    protected Integer itemType;
    /** 基础道具Id */
    protected Integer itemId;
    /** 道具数量 */
    protected Integer num;
    /** 装备/道具所在的背包 */
    protected Integer bagPack;
    /** 装备/道具所在背包的位置 */
    protected Integer bagIndex;
    /** 是否绑定 */
    protected Integer bound;
    /** 物品所属角色 */
    protected Integer playerId;
    /** 过期时间 */
    private long expireTime;
}
