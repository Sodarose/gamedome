package com.game.gameserver.module.goods.model;

import com.game.gameserver.module.goods.entity.Goods;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存
 * */
public abstract class AbstractContainer<T extends Goods> {
    protected Map<Integer,T> rawData;
    protected ReentrantReadWriteLock lock;

    protected transient List<T> ADD;
    protected transient List<T> UPDATE;
    protected transient List<T> REMOVE;

    public abstract boolean add(T t);
    public abstract boolean remove(T t,int num);
}
