package com.game.module.player.entity;

import com.game.module.bag.entity.Bag;
import com.game.module.equip.entity.EquipBar;
import com.game.module.player.model.Property;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/5/26 15:38
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

    /** 角色可变属性 */
    private Property property;

    /** 装备栏 */
    private EquipBar equipBar;

    /** 背包 */
    private Bag bag;
}
