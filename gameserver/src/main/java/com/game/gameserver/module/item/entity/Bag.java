package com.game.gameserver.module.item.entity;

import java.util.*;

/**
 * 背包实体
 *
 * @author xuewenkang
 * @date 2020/5/25 18:11
 */
public class BagEntity {

    private final static int MAX_CELL_LENGTH = 36;

    private Cell[] cells = new Cell[MAX_CELL_LENGTH];
    /** 快速索引用 */
    private Map<Integer,Item> itemMap = new HashMap<>(16);

    public BagEntity(){

    }

    public void init(List<Item> items){
        for(int i=0;i<MAX_CELL_LENGTH;i++){
            cells[i] = new Cell(i);
        }

        for(Item item:items){
            cells[item.getItemModel().getBagIndex()].setItem(item);
            itemMap.put(item.getItemModel().getId(),item);
        }
    }

    public Cell[] getCells(){
        return cells;
    }
}
