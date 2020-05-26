package com.game.module.equip.entity;

import com.game.module.player.entity.Player;
import lombok.Data;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/5/26 15:44
 */
@Data
public class EquipBar {
    /** 最大可装备数量 */
    public final static int MAX_EQUIP_LENGTH = 13;

    private Equip[] equips = new Equip[MAX_EQUIP_LENGTH];

    private Player player;

    public void init(List<Equip> equips){
        for(Equip equip:equips){
            this.equips[equip.getPart()] = equip;
        }
    }

    public void bind(Player player){
        this.player = player;
    }

}
