package com.game.gameserver.module.item.entity;

import com.game.protocol.ItemProtocol;
import lombok.Data;

import java.util.List;

/**
 * 装备栏
 * @author xuewenkang
 * @date 2020/5/26 10:39
 */
@Data
public class EquipBarEntity {
    /** 最大可装备数量 */
    private final static int MAX_EQUIP_LENGTH = 13;
    private Item[] items = new Item[MAX_EQUIP_LENGTH];

    public EquipBarEntity(){

    }

    public void init(List<Item> itemList){
        for(Item item:itemList){

        }
    }


    public ItemProtocol.EquipBarInfo getEquipBarInfo(){
        ItemProtocol.EquipBarInfo.Builder builder = ItemProtocol.EquipBarInfo.newBuilder();
        for(Item item:items){
            if(item==null){
                continue;
            }
            builder.addItemInfo(item.getItemInfo());
        }
        return builder.build();
    }

}
