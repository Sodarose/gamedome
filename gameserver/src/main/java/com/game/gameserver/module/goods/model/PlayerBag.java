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

    private int bagType;
    private int capacity;
    /** key 表示位置 */
    private Map<Integer,Goods> rawData;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private transient final List<Goods> ADD = new ArrayList<>();
    private transient final List<Goods> UPDATE = new ArrayList<>();
    private transient final List<Goods> REMOVE = new ArrayList<>();

    public PlayerBag(int capacity){

    }
}
