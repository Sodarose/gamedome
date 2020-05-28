package com.game.gameserver.module.bag.entity;

import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.player.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 背包实体
 *
 * @author xuewenkang
 * @date 2020/5/25 18:11
 */
public class Bag {
    /**
     * 最大格子数目
     */
    private final static int MAX_CELL_LENGTH = 36;

    /**
     * 背包格子
     */
    private Cell[] cells = new Cell[MAX_CELL_LENGTH];

    /**
     * 背包ID
     */
    private Integer id;
    private String  name;
    private Integer type;
    private Integer roleId;
    private Player  player;

    /** 是否打开 */
    private boolean open = false;

    public Bag() {

    }

    public Bag(Integer id, String name, Integer type, Integer roleId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.roleId = roleId;
    }

    public void bind(Player player) {
        this.player = player;
    }

    /**
     * 初始化格子
     * @param cellList 道具列表
     */
    public void init(List<Cell> cellList) {
        for (int i = 0; i < cells.length; i++) {
            Cell cell = new Cell(id, i);
            cells[i] = cell;
        }
        for(Cell cell:cellList){
            cells[cell.getBagIndex()] = cell;
        }
    }

    public Integer getId(){
        return id;
    }

    /**
     * 返回所有的各自列表（不管有没有物品）
     * @return java.util.List<com.game.gameserver.module.bag.entity.Cell>
     */
    public List<Cell> getAllCellList(){
        return Arrays.asList(cells);
    }

    /**
     * 返回装有物品的格子
     * @return java.util.List<com.game.gameserver.module.bag.entity.Cell>
     */
    public List<Cell> getNotNullCellList(){
        List<Cell> cellList = new ArrayList<>();
        for(Cell cell:cells){
            if(cell.getItem()==null){
                continue;
            }
            cellList.add(cell);
        }
        return cellList;
    }

    public Item getItem(Integer bagIndex,Integer itemId){
        Cell cell = cells[bagIndex];
        if(cell.getItem()==null||!cell.getItem().getId().equals(itemId)){
            return null;
        }
        return cell.getItem();
    }

    public String getName(){
        return name;
    }

    public void open(){
        open = true;
    }

    public void close(){
        open = false;
    }

    public boolean isOpen(){
        return open;
    }
}
