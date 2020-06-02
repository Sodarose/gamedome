package com.game.gameserver.module.player.entity;

import com.game.gameserver.dictionary.entity.CareerLevelPropertyData;
import com.game.gameserver.module.item.entity.Item;
import com.game.protocol.PlayerProtocol;
import lombok.Data;

/**
 * 属性
 * @author xuewenkang
 * @date 2020/5/26 11:53
 */
@Data
public class PropertyEntity {
    private int hp;
    private int mp;
    private int attack;
    private int defense;

    public PropertyEntity(){

    }

    public PropertyEntity(int hp,int mp,int attack,int defense){
        this.hp = hp;
        this.mp = mp;
        this.attack = attack;
        this.defense = defense;
    }

    public PropertyEntity(CareerLevelPropertyData propertyData){

    }
}
