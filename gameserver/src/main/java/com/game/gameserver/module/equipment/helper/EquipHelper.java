package com.game.gameserver.module.equipment.helper;

import com.game.gameserver.module.equipment.model.PlayerEquipBar;
import com.game.gameserver.module.equipment.type.PartType;
import com.game.gameserver.module.item.model.Item;

import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/12 12:35
 */
public class EquipHelper {
    public static String buildEquipBar(PlayerEquipBar playerEquipBar){
        StringBuilder sb = new StringBuilder("装备信息:");
        Map<Integer,Item> equipMap = playerEquipBar.getEquipMap();
        sb.append("武器:").append(equipMap.get(PartType.WEAPON).getItemConfig().getName())
                .append("(").append(equipMap.get(PartType.WEAPON).getDurability()).append("/")
                .append(equipMap.get(PartType.WEAPON).getItemConfig().getMaxDurability())
                .append(")").append("\n");
        sb.append("上衣:").append(equipMap.get(PartType.CLOTHE).getItemConfig().getName())
                .append("(").append(equipMap.get(PartType.CLOTHE).getDurability()).append("/")
                .append(equipMap.get(PartType.CLOTHE).getItemConfig().getMaxDurability())
                .append(")").append("\n");
        sb.append("肩甲:").append(equipMap.get(PartType.SHOULDER).getItemConfig().getName())
                .append("(").append(equipMap.get(PartType.SHOULDER).getDurability()).append("/")
                .append(equipMap.get(PartType.SHOULDER).getItemConfig().getMaxDurability())
                .append(")").append("\n");
        sb.append("腰带:").append(equipMap.get(PartType.BELT).getItemConfig().getName())
                .append("(").append(equipMap.get(PartType.BELT).getDurability()).append("/")
                .append(equipMap.get(PartType.BELT).getItemConfig().getMaxDurability())
                .append(")").append("\n");
        sb.append("裤子:").append(equipMap.get(PartType.TROUSER).getItemConfig().getName())
                .append("(").append(equipMap.get(PartType.TROUSER).getDurability()).append("/")
                .append(equipMap.get(PartType.TROUSER).getItemConfig().getMaxDurability())
                .append(")").append("\n");
        sb.append("鞋子:").append(equipMap.get(PartType.SHOE).getItemConfig().getName())
                .append("(").append(equipMap.get(PartType.SHOE).getDurability()).append("/")
                .append(equipMap.get(PartType.SHOE).getItemConfig().getMaxDurability())
                .append(")").append("\n");
        sb.append("称号:").append(equipMap.get(PartType.TITLE).getItemConfig().getName())
                .append("(").append(equipMap.get(PartType.TITLE).getDurability()).append("/")
                .append(equipMap.get(PartType.TITLE).getItemConfig().getMaxDurability())
                .append(")").append("\n");
        return sb.toString();
    }

}
