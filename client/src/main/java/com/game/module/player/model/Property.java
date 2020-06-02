package com.game.module.player.model;

import com.game.protocol.PlayerProtocol;
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

    public Property(PlayerProtocol.PropertyInfo propertyInfo){
        this.hp = propertyInfo.getHp();
        this.mp = propertyInfo.getMp();
        this.phyAttack = propertyInfo.getPhyAttack();
        this.phyDefense = propertyInfo.getPhyDefense();
        this.magicAttack = propertyInfo.getMagicAttack();
        this.magicDefense = propertyInfo.getMagicDefense();
        this.attackSpeed = propertyInfo.getAttackSpeed();
        this.moveSpeed = propertyInfo.getMoveSpeed();
    }
}
