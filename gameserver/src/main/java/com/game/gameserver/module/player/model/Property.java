package com.game.gameserver.module.player.model;

import com.game.gameserver.dictionary.dict.DictRoleLevelProperty;
import com.game.gameserver.module.equip.entity.Equip;
import lombok.Data;

/**
 * 属性
 * @author xuewenkang
 * @date 2020/5/26 11:53
 */
@Data
public class Property {
    private Integer hp;
    private Integer mp;
    private Integer phyAttack;
    private Integer magicAttack;
    private Integer phyDefense;
    private Integer magicDefense;
    private Double attackSpeed;
    private Double moveSpeed;

    public Property(){

    }

    public Property(DictRoleLevelProperty property){
        if(property==null){
            return;
        }
        this.hp = property.getHp();
        this.mp = property.getMp();
        this.phyAttack = property.getPhyAttack();
        this.phyDefense = property.getPhyDefense();
        this.magicAttack = property.getMagicAttack();
        this.magicDefense = property.getMagicDefense();
        this.attackSpeed = property.getAttackSpeed();
        this.moveSpeed = property.getMoveSpeed();
    }

    /**
     * 增加目标装备属性
     * @param equip 目标装备
     * @return void
     */
    public void addEquipProperty(Equip equip){
        this.hp +=equip.getDictEquip().getHp();
        this.mp +=equip.getDictEquip().getMp();
        this.phyAttack +=equip.getDictEquip().getPhyAttack();
        this.phyDefense +=equip.getDictEquip().getPhyDefense();
        this.magicAttack +=equip.getDictEquip().getMagicAttack();
        this.magicDefense +=equip.getDictEquip().getMagicDefense();
        this.attackSpeed +=equip.getDictEquip().getAttackSpeed();
        this.moveSpeed +=equip.getDictEquip().getMoveSpeed();
    }

    /**
     * 移除该目标装备属性
     * @param equip 目标装备
     * @return void
     */
    public void removeEquipProperty(Equip equip){
        this.hp -=equip.getDictEquip().getHp();
        this.mp -=equip.getDictEquip().getMp();
        this.phyAttack -=equip.getDictEquip().getPhyAttack();
        this.phyDefense -=equip.getDictEquip().getPhyDefense();
        this.magicAttack -=equip.getDictEquip().getMagicAttack();
        this.magicDefense -=equip.getDictEquip().getMagicDefense();
        this.attackSpeed -=equip.getDictEquip().getAttackSpeed();
        this.moveSpeed -=equip.getDictEquip().getMoveSpeed();
    }
}
