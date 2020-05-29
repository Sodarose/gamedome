package com.game.gameserver.module.bag.entity;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.model.ItemType;
import com.game.gameserver.module.player.entity.Player;

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

    private Integer id;
    private String  name;
    private Integer type;
    private Integer roleId;
    private Player  player;

    private Map<Integer,Item> itemMap = new HashMap<>(16);
    private Map<Integer,Equip> equipMap = new HashMap<>(16);

    public Bag(Integer id, String name, Integer type, Integer roleId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.roleId = roleId;
    }

    public void bind(Player player) {
        this.player = player;
    }

    /** 初始话背包 */
    public void init(List<Item> items,List<Equip> equips) {
        for(int i=0;i<cells.length;i++){
            cells[i] = new Cell();
        }

        for(Item item:items){
            itemMap.put(item.getId(),item);
            cells[item.getBagIndex()].setItem(item);
        }

        for(Equip equip:equips){
            equipMap.put(equip.getId(),equip);
            cells[equip.getBagIndex()].setItem(equip);
        }

    }

    public Integer getId(){
        return id;
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

    public boolean hasEquip(Integer equipId){
        return equipMap.containsKey(equipId);
    }

    public boolean hasItem(Integer itemId){
        return itemMap.containsKey(itemId);
    }

    public Equip getEquip(Integer equipId){
        return  equipMap.get(equipId);
    }

    public Item getItem(Integer itemId){
        return itemMap.get(itemId);
    }

    public void putInEquip(Equip equip){
        if(equip.getBagIndex()!=null){
            cells[equip.getBagIndex()].setItem(equip);
            return;
        }
        for(int i=0;i<cells.length;i++){
            Cell cell = cells[i];
            if(cell.getItem()==null){
                cell.setItem(equip);
                return;
            }
        }
    }
}
