package com.game.gameserver.module.goods.model;


import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.entity.Goods;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 玩家装备栏
 *
 * @author xuewenkang
 * @date 2020/6/7 10:26
 */
public class EquipBag  {
    /** 容量 */
    private int capacity;
    /** 容器 key为格子Id value 为装备Id */
    private Map<Integer,Goods> rawData;
    private ReentrantReadWriteLock lock;
}
