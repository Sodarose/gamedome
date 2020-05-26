package com.game.gameserver.module.equip.model;

import com.game.gameserver.module.item.model.ItemModel;
import lombok.Data;

/**
 *
 * @author xuewenkang
 * @date 2020/5/25 17:59
 */
@Data
public class EquipModel extends ItemModel {
    private Integer id;
    /** 基本数据的ID */
    private Integer equipId;
    /** 当前耐久度 */
    private Integer durability;
    /** 是否装备 1:装备 0:没有装备 */
    private Integer equipment;
}
