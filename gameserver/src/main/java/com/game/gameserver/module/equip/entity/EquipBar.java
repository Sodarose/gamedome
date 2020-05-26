package com.game.gameserver.module.equip.entity;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.Property;

import java.util.List;

/**
 * 装备栏
 * @author xuewenkang
 * @date 2020/5/26 10:39
 */
public class EquipBar {
    /** 最大可装备数量 */
    private final static int MAX_EQUIP_LENGTH = 13;
    private Equip[] equips = new Equip[MAX_EQUIP_LENGTH];

    private Player player;

    public EquipBar(Player player){
        this.player = player;
    }

    public Equip[] getEquipEntities(){
        return equips;
    }

    private boolean first = true;

    /**
     * 初始化
     * @param equipEntities 装备列表
     * @return void
     */
    public void init(List<Equip> equipEntities){
        if(equipEntities==null){
            return;
        }
        for(Equip equip :equipEntities){
            putEquip(equip);
        }
        first = false;
    }

    /**
     * 穿上装备
     * @param equip 装备
     * @return equip 返回被卸下的装备
     */
    public Equip putEquip(Equip equip){
        // 被卸下的装备 如果没有 则为NULL
        Equip takeEquip = equips[equip.getDictEquip().getPart()];
        equips[equip.getDictEquip().getPart()] = equip;
        if(takeEquip!=null){
            player.getProperty().removeEquipProperty(takeEquip);
        }
        player.getProperty().addEquipProperty(equip);
        updatePlayer();
        return  takeEquip;
    }

    /**
     * 脱下装备
     * @param equip 装备
     * @return equip 卸下的装备
     */
    public Equip takeEquip(Equip equip){
       Equip takeEquip =  equips[equip.getDictEquip().getPart()];
       equips[equip.getDictEquip().getPart()] = null;
       if(takeEquip!=null){
           player.getProperty().removeEquipProperty(equip);
           updatePlayer();
       }
       return takeEquip;
    }

    private void updatePlayer(){
        if(first){
            return;
        }
        player.update();
    }
}
