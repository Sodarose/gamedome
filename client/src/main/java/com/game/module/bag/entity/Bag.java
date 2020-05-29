package com.game.module.bag.entity;

import lombok.Data;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/27 15:33
 */
@Data
public class Bag {
    /**
     * 最大格子数目
     */
    private final static int MAX_CELL_LENGTH = 36;

    /**
     * 背包格子
     */
    private Cell[] cells = new Cell[MAX_CELL_LENGTH];
    private Integer id;
    private String  name;

    public void init(List<Cell> cellList) {
        for (int i = 0; i < cells.length; i++) {
            Cell cell = new Cell(i);
            cells[i] = cell;
        }
        for(Cell cell:cellList){
            cells[cell.getBagIndex()] = cell;
        }
    }

    public Cell getCellByItemName(String itemName){
        for(Cell cell:cells){
            if(cell.getItemName().equals(itemName)){
                return cell;
            }
        }
        return null;
    }
}
