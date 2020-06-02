package com.game.gameserver.module.player.entity;

import com.game.gameserver.module.item.entity.Item;
import com.game.protocol.PlayerProtocol;
import lombok.Data;

/**
 * 属性
 * @author xuewenkang
 * @date 2020/5/26 11:53
 */
@Data
public class PropertyEntity {
    private Integer hp;
    private Integer mp;
    private Integer phyAttack;
    private Integer magicAttack;
    private Integer phyDefense;
    private Integer magicDefense;
    private Double attackSpeed;
    private Double moveSpeed;

    public PropertyEntity(){

    }

    public PropertyEntity(PropertyEntity propertyEntity){

    }

    /**
     * 增加一件装备的属性
     * */
    public void addEquipProperty(Item item){

    }

    /**
     * 删除一件装备的属性
     * */
    public void removeEquipProperty(Item item){

    }

    public PlayerProtocol.PropertyInfo getProperInfo(){
        PlayerProtocol.PropertyInfo.Builder builder = PlayerProtocol.PropertyInfo.newBuilder();
        builder.setHp(hp);
        builder.setMp(mp);
        builder.setPhyAttack(phyAttack);
        builder.setPhyDefense(phyDefense);
        builder.setMagicAttack(magicAttack);
        builder.setMagicDefense(magicDefense);
        builder.setAttackSpeed(attackSpeed);
        builder.setMoveSpeed(moveSpeed);
        return builder.build();
    }


}
