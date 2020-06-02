package com.game.module.item.entity;

import com.game.protocol.ItemProtocol;
import lombok.Data;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/26 15:44
 */
@Data
public class EquipBar {
    /** 最大可装备数量 */
    public final static int MAX_EQUIP_LENGTH = 13;

    private ItemProtocol.ItemInfo[] itemInfos = new ItemProtocol.ItemInfo[MAX_EQUIP_LENGTH];

    public EquipBar(){

    }

    public EquipBar(ItemProtocol.EquipBarInfo equipBarInfo){
        init(equipBarInfo.getItemInfoList());
    }

    public void init(List<ItemProtocol.ItemInfo> itemInfoList){
        for(ItemProtocol.ItemInfo itemInfo:itemInfoList){
            this.itemInfos[itemInfo.getDictEquipInfo().getProperty().getPart()] = itemInfo;
        }
    }
}