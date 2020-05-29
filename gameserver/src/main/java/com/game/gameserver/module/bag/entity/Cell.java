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
@Data
public class Cell {
    /** 装载的道具 */
    private Item item;
    public Cell(){

    }
}
