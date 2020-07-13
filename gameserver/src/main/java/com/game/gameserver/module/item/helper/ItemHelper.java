package com.game.gameserver.module.item.helper;

import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;

/**
 * @author xuewenkang
 * @date 2020/7/12 13:28
 */
public class ItemHelper {
    public static String buildItem(Item item){
        StringBuilder sb = new StringBuilder("道具信息:");
        sb.append("type:").append(item.getItemConfig().getType().equals(ItemType.CONSUMABLES.getType())?"道具":"装备")
                .append("\n");
        sb.append("name:").append(item.getItemConfig().getName()).append("\n");
        if(item.getItemConfig().getType().equals(ItemType.EQUIP.getType())){
            sb.append("durability:").append(item.getDurability()).append("/").append(item.getItemConfig()
                    .getMaxDurability()).append("\n");
        }
        sb.append("buffer:").append(item.getItemConfig().getBufferId()).append("\n");
        sb.append("property:").append("\n");
        item.getItemConfig().getPropertyMap().forEach((key,value)->{
            sb.append(key).append(":").append(value).append("\n");
        });
        return sb.toString();
    }
}
