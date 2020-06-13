package com.game.gameserver.module.goods.entity;

import lombok.Data;

/**
 * 物品基础父类
 *
 * @author xuewenkang
 * @date 2020/6/11 14:24
 */
@Data
public class Goods {
    /** 物品唯一Id */
    protected Integer id;
    /** 数据库Id */
    protected Integer pkId;
    /** 物品类型  */
    protected Integer goodsType;
    /** 物品Id */
    protected Integer goodsId;
    /** 物品数量 */
    protected Integer count;
    /** 物品所在的背包 */
    protected Integer bagPack;
    /** 物品所在背包的位置 */
    protected Integer bagIndex;
    /** 是否绑定 */
    protected Integer bound;
    /** 物品所属角色 */
    protected Integer playerId;
}
