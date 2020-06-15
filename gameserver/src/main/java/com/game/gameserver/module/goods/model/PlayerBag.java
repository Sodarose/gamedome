package com.game.gameserver.module.goods.model;

import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.entity.Goods;
import com.game.gameserver.module.goods.entity.Prop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用户背包
 *
 * @author xuewenkang
 * @date 2020/6/10 10:28
 */
public class PlayerBag {

    private final int bagType;
    private final int capacity;
    /** key 表示位置 */
    private final Map<Integer,Goods> rawData;
    private final ReentrantReadWriteLock lock;

    public PlayerBag(int capacity){
        this.bagType = BagType.NORMAL_BAG;
        this.capacity = capacity;
        this.rawData = new ConcurrentHashMap<>(capacity);
        this.lock = new ReentrantReadWriteLock();
    }

    public boolean add(Goods goods){
        return false;
    }

    public boolean remove(int goodsId,int num){
        return false;
    }

    public boolean remove(int bagIndex,int goodsId,int num){
        return false;
    }

    public boolean hasSpace(){
        return false;
    }

    public List<Goods> getGoodsList(){
        return null;
    }
}
