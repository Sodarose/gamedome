package com.game.gameserver.module.goods.model;


import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.entity.Goods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 玩家装备栏
 *
 * @author xuewenkang
 * @date 2020/6/7 10:26
 */
public class EquipBar {
    /** 用户ID */
    private int playerId;
    /** 装备栏 */
    private int capacity;
    /** 物品容器 */
    private Equip[] rawData;

    public EquipBar(int playerId,int capacity){
        this.playerId = playerId;
        this.capacity = capacity;
        this.rawData = new Equip[capacity];
    }

    public void initialize(List<Equip> equips){
        for(Equip equip:equips){
            rawData[equip.getBagIndex()] = equip;
        }
    }

    public Equip[] getRawData(){
        return rawData;
    }

    /**
     * 穿上装备
     *
     * @param equip 被替换下来的装备 如果本身该位置没有装备 则为NULL
     * @return com.game.gameserver.module.goods.entity.Equip
     */
    public Equip put(Equip equip){
        // 拿到将要卸下的装备
        Equip takeEquip = rawData[equip.getBagIndex()];
        // 移除该位置的装备
        rawData[equip.getBagIndex()]=null;
        // 放入新装备
        rawData[equip.getBagIndex()]=equip;
        return takeEquip;
    }

    /**
     * 脱下装备
     *
     * @param equipId
     * @return com.game.gameserver.module.goods.entity.Equip
     */
    public Equip take(int equipId){
        Equip takeEquip = null;
        for(Equip equip:rawData){
            if(equip.getId().equals(equipId)){
                takeEquip=equip;
                rawData[equip.getBagIndex()] = null;
                break;
            }
        }
        return takeEquip;
    }

    public List<Equip> getEquipList(){
        List<Equip> equipList = new ArrayList<>();
        equipList.addAll(Arrays.asList(rawData));
        return equipList;
    }
}
