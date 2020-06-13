package com.game.gameserver.module.goods.model;

import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.entity.Goods;
import com.game.gameserver.module.goods.entity.Prop;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用户背包
 *
 * @author xuewenkang
 * @date 2020/6/10 10:28
 */
public class PlayerBag {
    /** 用户ID */
    private int playerId;
    /** 背包容量 */
    private int capacity;
    /** 物品容器 */
    private Goods[] rawData;
    /** 读写锁 */
    private transient ReentrantReadWriteLock lock;


    public PlayerBag(int playerId,int capacity) {
        this.playerId = playerId;
        this.capacity = capacity;
        this.rawData = new Goods[capacity];
        this.lock = new ReentrantReadWriteLock();
    }

    public void initialize(List<Goods> goodsList){
        for(Goods goods:goodsList){
            rawData[goods.getBagIndex()] = goods;
        }
    }

    /**
     * 添加装备
     *
     * @param equip
     * @return boolean
     */
    public boolean addEquip(Equip equip){
        // 如果指定了位置 那么就判断该位置是否有东西 否则直接放入
        if(equip.getBagIndex()!=null){
            if(rawData[equip.getBagIndex()]!=null){
                return false;
            }
            rawData[equip.getBagIndex()]=equip;
            return true;
        }
        // 否则找到数组中左边第一个为空的格子 放入该装备
        for(int i=0;i<rawData.length;i++){
            if(rawData[i]==null){
                equip.setBagIndex(i);
                rawData[i] = equip;
            }
        }
        return true;
    }

    /**
     * 添加道具
     *
     * @param prop
     * @return boolean
     */
    public boolean addProp(Prop prop){
        return false;
    }

    /**
     * 移除装备
     *
     * @param equipId
     * @return equip 移除的装备
     */
    public boolean removeEquip(int equipId){
        return false;
    }

    /**
     * 移除道具
     *
     * @param propId
     * @param count
     * @return boolean
     */
    public boolean removeProp(int propId,int count){
        return false;
    }

    /**
     * 是否有剩余空间
     *
     * @param goods
     * @return boolean
     */
    public boolean hasSpace(Collection<Goods> goods){
        return false;
    }

    /**
     * 移动物品
     *
     * @param goodsId
     * @param bagIndex
     * @return boolean
     */
    public boolean moveGoods(int goodsId,int bagIndex){
        return false;
    }

    public Equip getEquip(int equipId){
        return null;
    }

    public Prop getProp(int propId){
        return null;
    }

    public Prop getProp(int propId,int count){
        return null;
    }
}
