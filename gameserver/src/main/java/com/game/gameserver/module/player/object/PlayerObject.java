package com.game.gameserver.module.player.object;

import com.game.gameserver.module.buffer.entity.BufferBarEntity;
import com.game.gameserver.module.item.entity.BagEntity;
import com.game.gameserver.module.item.entity.Equip;
import com.game.gameserver.module.item.entity.EquipBarEntity;
import com.game.gameserver.module.player.entity.Career;
import com.game.gameserver.module.player.model.PlayerModel;
import com.game.gameserver.module.player.entity.Property;
import com.game.gameserver.module.skill.entity.SkillBarEntity;
import org.springframework.beans.BeanUtils;

/**
 * @author xuewenkang
 * @date 2020/6/2 21:35
 */
public class NewPlayerObject {
    /** 角色唯一ID */
    private final int id;
    /** 基本信息 */
    private final PlayerModel playerModel;
    private Career career;
    /** 属性 */
    private Property property;
    /** 装备栏 */
    private EquipBarEntity equipBarEntity;
    /** 背包 */
    private BagEntity bagEntity;
    /** 技能 */
    private SkillBarEntity skillBarEntity;
    /** buff*/
    private BufferBarEntity bufferBarEntity;

    public NewPlayerObject(int id,PlayerModel playerModel){
        this.id = id;
        this.playerModel = playerModel;
    }

    public void initProperty(){
        property = new Property();
        BeanUtils.copyProperties(career.getLevelProperty().getPropertyData(),property);
    }

    /** 调整属性 该方法将初始化所有属性 */
    public void adjustProperty(){
        initProperty();
        Equip[] equips = equipBarEntity.getEquips();
        for(Equip equip:equips){
            property.addEquipProperty(equip);
        }
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public Career getCareer() {
        return career;
    }

    public BagEntity getBagEntity() {
        return bagEntity;
    }

    public void setBagEntity(BagEntity bagEntity) {
        this.bagEntity = bagEntity;
    }

    public Integer getId() {
        return id;
    }

    public BufferBarEntity getBufferBarEntity() {
        return bufferBarEntity;
    }

    public void setBufferBarEntity(BufferBarEntity bufferBarEntity) {
        this.bufferBarEntity = bufferBarEntity;
    }

    public EquipBarEntity getEquipBarEntity() {
        return equipBarEntity;
    }

    public void setEquipBarEntity(EquipBarEntity equipBarEntity) {
        this.equipBarEntity = equipBarEntity;
    }

    public void setSkillBarEntity(SkillBarEntity skillBarEntity) {
        this.skillBarEntity = skillBarEntity;
    }

    public SkillBarEntity getSkillBarEntity() {
        return skillBarEntity;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
