package com.game.gameserver.module.goods;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 抽象容器
 *
 * @author xuewenkang
 * @date 2020/6/10 16:18
 */
public abstract class AbstractContainer<T extends IGoods> {
    /** 背包类型 */
    protected int bagType;
    protected int capacity;
    protected List<T> rawData;
    protected transient ReentrantReadWriteLock lock;

}
