package com.game.gameserver.module.goods.model;

import com.game.gameserver.module.goods.entity.Prop;

import java.util.Collection;

public class PropContainer extends AbstractContainer<Prop>{
    @Override
    public boolean add(Prop prop) {
        return false;
    }

    @Override
    public boolean remove(Prop prop, int num) {
        return false;
    }

}
