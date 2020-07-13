package com.game.gameserver.module.item.model;

import com.game.gameserver.common.config.ItemConfig;

/**
 * 道具模型
 *
 * @author xuewenkang
 * @date 2020/7/12 2:26
 */
public class ItemEntity extends com.game.gameserver.module.item.entity.ItemEntity {
    /** 道具静态资源属性 */
    private final ItemConfig itemConfig;

    public ItemEntity(ItemConfig itemConfig){
        this.itemConfig = itemConfig;
    }

    public ItemConfig getItemConfig() {
        return itemConfig;
    }

}
