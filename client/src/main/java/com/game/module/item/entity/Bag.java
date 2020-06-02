package com.game.module.item.entity;

import com.game.protocol.ItemProtocol;
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
    private String  name = "背包";

    public Bag(){

    }

    public Bag(ItemProtocol.BagInfo bagInfo){
        init(bagInfo.getCellInfoList());
    }

    public void init(List<ItemProtocol.CellInfo> cellInfos) {
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell(i);
        }
        for(ItemProtocol.CellInfo cellInfo:cellInfos){
            cells[cellInfo.getBagIndex()].setItemInfo(cellInfo.getItemInfo());
        }
    }
}
