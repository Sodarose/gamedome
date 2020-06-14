package com.game.gameserver.module.goods.model;

import com.game.gameserver.module.goods.entity.Equip;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 装备容器
 * */
public class EquipContainer extends AbstractContainer<Equip> {



    public EquipContainer(){
        this.rawData = new ConcurrentHashMap<>(16);
    }

    @Override
    public boolean add(Equip equip) {
        return false;
    }

    @Override
    public boolean remove(Equip equip, int num) {
        return false;
    }
}
