package com.game.gameserver.module.item.entity;

import com.game.gameserver.module.player.object.PlayerObject;

import java.util.*;

/**
 * 背包实体
 *
 * @author xuewenkang
 * @date 2020/5/25 18:11
 */
public class Bag {

    private final static int MAX_CELL_LENGTH = 36;

    private Cell[] cells = new Cell[MAX_CELL_LENGTH];

    private Map<Integer,Item> itemMap = new HashMap<>(16);

    public Bag(){

    }

    public void init(List<Item> items){
        for(int i=0;i<cells.length;i++){
            cells[i] = new Cell(i);
        }
        for(Item item:items){
            cells[item.getBagIndex()].setItem(item);
            itemMap.put(item.getId(),item);
        }
    }


}
