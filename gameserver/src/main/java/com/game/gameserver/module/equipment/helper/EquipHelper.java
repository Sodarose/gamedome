package com.game.gameserver.module.equipment.helper;

import com.game.gameserver.module.equipment.model.EquipBar;
import com.game.gameserver.module.equipment.type.PartType;
import com.game.gameserver.module.item.model.Item;

import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/7/12 12:35
 */
public class EquipHelper {
    public static String buildEquipBar(EquipBar equipBar){
        StringBuilder sb = new StringBuilder("装备信息:");
        Map<Integer,Item> equipMap = equipBar.getEquipMap();
        sb.append("武器:").append(equipMap.get(PartType.WEAPON)==null?"空":equipMap.get(PartType.WEAPON).getItemConfig()
                .getName()).append("\n");
        sb.append("上衣:").append(equipMap.get(PartType.CLOTHE)==null?"空":equipMap.get(PartType.CLOTHE).getItemConfig()
                .getName()).append("\n");
        sb.append("肩甲:").append(equipMap.get(PartType.SHOULDER)==null?"空":equipMap.get(PartType.SHOULDER).getItemConfig()
                .getName()).append("\n");
        sb.append("腰带:").append(equipMap.get(PartType.BELT)==null?"空":equipMap.get(PartType.BELT).getItemConfig()
                .getName()).append("\n");
        sb.append("裤子:").append(equipMap.get(PartType.TROUSER)==null?"空":equipMap.get(PartType.TROUSER).getItemConfig()
                .getName()).append("\n");
        sb.append("鞋子:").append(equipMap.get(PartType.SHOE)==null?"空":equipMap.get(PartType.SHOE).getItemConfig()
                .getName()).append("\n");
        sb.append("称号:").append(equipMap.get(PartType.TITLE)==null?"空":equipMap.get(PartType.TITLE).getItemConfig()
                .getName()).append("\n");
        return sb.toString();
    }

}
