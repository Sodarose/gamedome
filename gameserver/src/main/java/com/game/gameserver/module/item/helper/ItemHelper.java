package com.game.gameserver.module.item.helper;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.item.type.ItemType;

/**
 * @author xuewenkang
 * @date 2020/7/12 13:28
 */
public class ItemHelper {
    public static String buildItem(Item item){
        StringBuilder sb = new StringBuilder("道具信息:");
        ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(item.getItemConfigId());
        sb.append("type:").append(itemConfig.getType().equals(ItemType.CONSUMABLES.getType())?"道具":"装备")
                .append("\n");
        sb.append("name:").append(itemConfig.getName()).append("\n");
        if(itemConfig.getType().equals(ItemType.EQUIP.getType())){
            sb.append("durability:").append(item.getDurability()).append("/").append(itemConfig.getMaxDurability()).append("\n");
        }
        sb.append("buffer:").append(itemConfig.getBufferId()).append("\n");
        sb.append("property:").append("\n");

        return sb.toString();
    }

    public static String buildItemSimpleMsg(Item item){
        if(item==null){
            return "空";
        }
        StringBuilder sb = new StringBuilder();
        ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(item.getItemConfigId());
        if(itemConfig.getType().equals(ItemType.CONSUMABLES.getType())){
            sb.append(itemConfig.getName()).append("(").append(item.getNum()).append(")");
        }
        if(itemConfig.getType().equals(ItemType.EQUIP.getType())){
            sb.append(itemConfig.getName()).append("(").append(item.getDurability()).append(")");
        }
        return sb.toString();
    }
}
