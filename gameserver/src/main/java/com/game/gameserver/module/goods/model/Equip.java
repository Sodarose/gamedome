package com.game.gameserver.module.goods.model;

import com.game.gameserver.common.config.EquipConfig;
import com.game.gameserver.module.goods.AbstractGoods;

/**
 * 玩家装备模型
 *
 * @author xuewenkang
 * @date 2020/6/10 10:27
 */
public class Equip extends AbstractGoods {
    /** 装备静态属性 */
    private EquipConfig equipConfig;
    /** 当前耐久度 */
    private int maxDurability;
}
