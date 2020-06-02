package com.game.gameserver.module.item.entity;

import com.game.protocol.ItemProtocol;
import lombok.Data;

/**
 * 格子
 * @author xuewenkang
 * @date 2020/5/25 15:37
 */
@Data
public class CellEntity {
    private Integer bagIndex;
    private Item item;

    public CellEntity(){}

    public CellEntity(Integer bagIndex){
        this.bagIndex = bagIndex;
    }

    public CellEntity(Item item){
        this.item = item;
    }

    public ItemProtocol.CellInfo getCellInfo(){
        ItemProtocol.CellInfo.Builder builder = ItemProtocol.CellInfo.newBuilder();
        builder.setBagIndex(bagIndex);
        builder.setItemInfo(item.getItemInfo());
        return builder.build();
    }
}
