package com.game.gameserver.module.player.object;

import com.game.gameserver.module.buffer.entity.BufferBarEntity;
import com.game.gameserver.module.item.entity.BagEntity;
import com.game.gameserver.module.item.entity.EquipBarEntity;
import com.game.gameserver.module.player.entity.CareerEntity;
import com.game.gameserver.module.player.entity.PlayerEntity;
import com.game.gameserver.module.player.entity.PropertyEntity;
import com.game.gameserver.module.skill.entity.SkillBagEntity;

/**
 * @author xuewenkang
 * @date 2020/6/2 21:35
 */
public class NewPlayerObject {
    /** 角色唯一ID */
    private Integer id;
    /** 基本信息 */
    private PlayerEntity playerEntity;
    private CareerEntity careerEntity;
    /** 属性 */
    private PropertyEntity propertyEntity;
    /** 装备栏 */
    private EquipBarEntity equipBarEntity;
    /** 背包 */
    private BagEntity bagEntity;
    /** 技能 */
    private SkillBagEntity skillBagEntity;
    /** buff*/
    private BufferBarEntity bufferBarEntity;
}
