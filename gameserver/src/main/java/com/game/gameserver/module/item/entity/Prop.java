package com.game.gameserver.module.item.entity;

import com.game.gameserver.common.config.PropConfig;
import com.game.gameserver.module.item.type.ItemType;
import com.game.gameserver.util.GameUUID;

/**
 * 道具
 *
 * @author xuewenkang
 * @date 2020/6/10 10:28
 */
public class Prop extends Item {
    public static Prop valueOf(PropConfig propConfig){
        Prop prop = new Prop();
        prop.setId(GameUUID.getInstance().generate());
        prop.setItemType(ItemType.PROP);
        prop.setItemId(propConfig.getId());
        prop.setBound(0);
        return prop;
    }
}
