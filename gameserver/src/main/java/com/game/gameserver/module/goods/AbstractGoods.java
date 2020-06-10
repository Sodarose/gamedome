package com.game.gameserver.module.goods;

/**
 * 抽象物品类
 *
 * @author xuewenkang
 * @date 2020/6/10 16:21
 */
public abstract class AbstractGoods implements IGoods{
    /** 物品唯一ID */
    protected int id;
    /** 物品类型 */
    protected int type;
    /** 物品数量 */
    protected int count;
    /** 所在的背包类型 */
    protected int bagType;
    /** 所在的背包位置 */
    protected int bagIndex;
    /** 是否绑定 */
    protected boolean bound;
    /** 到期时间 */
    protected long expireTime;
}
