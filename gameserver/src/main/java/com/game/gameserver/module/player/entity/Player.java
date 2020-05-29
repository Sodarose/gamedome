package com.game.gameserver.module.player.entity;

import com.game.gameserver.dictionary.dict.DictRoleLevelProperty;
import com.game.gameserver.module.account.model.Account;
import com.game.gameserver.module.bag.entity.Bag;
import com.game.gameserver.module.equip.entity.Equip;
import com.game.gameserver.module.equip.entity.EquipBar;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.player.model.Property;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.List;

/**
 * 玩家角色实体
 * @author xuewenkang
 * @date 2020/5/25 0:10
 */
@Data
public class Player {
    /** 角色基本属性 */
    private Integer id;
    private String name;
    private Integer level;
    private Integer career;
    private Integer sceneId;
    private Integer userId;

    /** 基础等级属性 */
    private DictRoleLevelProperty levelProperty;

    /** 可变的 角色属性 */
    private Property property;

    /** account */
    private Account account;

    /** channel */
    private Channel channel;

    /** 装备栏（已经穿戴在身上的） */
    private EquipBar equipBar;

    /** 背包 */
    private Bag bag;


    public Player(){

    }

    public boolean putEquip(Integer equipId){
        if(bag==null){
            return false;
        }
        Equip equip = bag.getEquip(equipId);
        if(equip==null){
            return false;
        }
        Equip takeEquip = equipBar.putEquip(equip);
        if(takeEquip!=null){
            takeEquip.setBagIndex(equip.getBagIndex());
            bag.putInEquip(equip);
        }
        return true;
    }

}
