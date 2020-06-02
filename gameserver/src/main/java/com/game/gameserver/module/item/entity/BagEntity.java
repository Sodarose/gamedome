package com.game.gameserver.module.item.entity;

import com.game.gameserver.module.item.model.ItemProperty;
import com.game.protocol.ItemProtocol;

import java.util.*;

/**
 * 背包实体
 *
 * @author xuewenkang
 * @date 2020/5/25 18:11
 */
public class BagEntity {

    private final static int MAX_CELL_LENGTH = 36;

    private CellEntity[] cellEntities = new CellEntity[MAX_CELL_LENGTH];

    private Map<Integer,Item> itemMap = new HashMap<>(16);

    public BagEntity(){

    }

    public void init(List<Item> items){
        for(int i = 0; i< cellEntities.length; i++){
            cellEntities[i] = new CellEntity(i);
        }

        for(Item item:items){
            int bagIndex = item.getProperty().getInteger(ItemProperty.BAG_INDEX);
            cellEntities[bagIndex].setItem(item);
        }
    }



    public ItemProtocol.BagInfo getBagInfo(){
        ItemProtocol.BagInfo.Builder builder = ItemProtocol.BagInfo.newBuilder();
        for(CellEntity cellEntity : cellEntities){
            if(cellEntity.getItem()==null){
                continue;
            }
            builder.addCellInfo(cellEntity.getCellInfo());
        }
        return builder.build();
    }

}
