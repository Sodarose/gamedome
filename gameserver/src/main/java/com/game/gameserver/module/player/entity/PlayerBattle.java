package com.game.gameserver.module.player.entity;

import com.game.gameserver.common.config.CareerLevelProperty;
import com.game.gameserver.module.buffer.model.Buffer;
import com.game.gameserver.module.goods.entity.Equip;
import com.game.gameserver.module.goods.model.EquipBag;
import lombok.Data;

import java.util.List;

/**
 * 玩家角色战斗属性
 *
 * @author xuewenkang
 * @date 2020/6/10 10:22
 */
@Data
public class PlayerBattle {
    /**
     * hp
     */
    private int hp;
    /**
     * 当前hp
     */
    private int currHp;
    /**
     * mp
     */
    private int mp;
    /**
     * 当前mp
     */
    private int currMp;
    /**
     * 攻击力
     */
    private int attack;
    /**
     * 防御力
     */
    private int defense;

    public void initialize(CareerLevelProperty careerLevelProperty) {
        this.hp = careerLevelProperty.getHp();
        this.mp = careerLevelProperty.getMp();
        this.attack = careerLevelProperty.getAttack();
        this.defense = careerLevelProperty.getDefense();
    }

    /**
     * 添加装备栏属性
     *
     * @param equipBag
     * @return void
     */
    public void addEquipBarProperty(EquipBag equipBag) {
       /* Equip[] rawData = equipBag.getRawData();
        for (Equip equip : rawData) {
            if(equip==null){
                continue;
            }
            addEquipProperty(equip);
        }*/
    }


    /**
     * 添加一个装备属性
     *
     * @param equip
     * @return void
     */
    public void addEquipProperty(Equip equip) {
/*        this.hp += equip.getEquipConfig().getHp();
        this.mp += equip.getEquipConfig().getMp();
        this.attack+=equip.getEquipConfig().getAttack();
        this.defense+=equip.getEquipConfig().getDefense();*/
    }

    /**
     * 删除一件装备属性
     *
     * @param equip
     * @return void
     */
    public void removeEquipProperty(Equip equip) {
      /*  this.hp -= equip.getEquipConfig().getHp();
        this.mp -= equip.getEquipConfig().getMp();
        this.attack-=equip.getEquipConfig().getAttack();
        this.defense-=equip.getEquipConfig().getDefense();*/
    }

    /**
     * 复位 将血量回满
     *
     * @param
     * @return void
     */
    public void reset() {
        this.currHp = hp;
        this.currMp = mp;
    }

    /**
     * 调整属性
     *
     * @param
     * @return void
     */
    public void adjust() {
        if (currHp > hp) {
            currHp = hp;
        }
        if (currMp > mp) {
            currMp = mp;
        }
    }


    /**
     * 根据buffers 调整战斗属性
     *
     * @param buffers
     * @return void
     */
    public void addBufferListProperty(List<Buffer> buffers) {

    }

}
