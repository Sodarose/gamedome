package com.game.gameserver.module.bag.entity;

import com.game.gameserver.module.bag.model.CellModel;
import com.game.gameserver.module.item.entity.Item;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 格子
 * @author xuewenkang
 * @date 2020/5/25 15:37
 */
public class Cell {
    /** 保存到数据库Id */
    private Integer id;
    /** 装载的道具 */
    private Item item;
    /** 背包Id */
    private Integer bagId;
    /** 格子在背包中的位置*/
    private Integer bagIndex;
    /** 道具的数量 */
    private AtomicInteger count;

    public Cell(CellModel cellModel){
        this.id = cellModel.getId();
        this.bagId = cellModel.getBagId();
        this.bagIndex = cellModel.getBagIndex();
        this.count = new AtomicInteger(cellModel.getCount());
    }

    /** 得到道具 */
    public Item getItem(){
        return item;
    }

    /** 放入道具 */
    public void putItem(Item item){
        this.item = item;
    }

    public Cell(int bagId,int bagIndex){
        this.bagId = bagId;
        this.bagIndex = bagIndex;
    }

    public Integer getBagIndex(){
        return bagIndex;
    }

    public int getCount(){
        return count.get();
    }
}
