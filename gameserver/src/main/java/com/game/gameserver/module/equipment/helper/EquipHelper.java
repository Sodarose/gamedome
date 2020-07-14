package com.game.gameserver.module.equipment.helper;

import com.game.gameserver.module.equipment.model.EquipBar;
import com.game.gameserver.module.equipment.type.PartType;
import com.game.gameserver.module.item.helper.ItemHelper;
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
        sb.append("武器:").append(ItemHelper.buildItemSimpleMsg(equipMap.get(PartType.WEAPON))).append("\n");
        sb.append("上衣:").append(ItemHelper.buildItemSimpleMsg(equipMap.get(PartType.CLOTHE))).append("\n");
        sb.append("肩甲:").append(ItemHelper.buildItemSimpleMsg(equipMap.get(PartType.SHOULDER))).append("\n");
        sb.append("腰带:").append(ItemHelper.buildItemSimpleMsg(equipMap.get(PartType.BELT))).append("\n");
        sb.append("裤子:").append(ItemHelper.buildItemSimpleMsg(equipMap.get(PartType.TROUSER))).append("\n");
        sb.append("鞋子:").append(ItemHelper.buildItemSimpleMsg(equipMap.get(PartType.SHOE))).append("\n");
        sb.append("称号:").append(ItemHelper.buildItemSimpleMsg(equipMap.get(PartType.TITLE))).append("\n");
        return sb.toString();
    }

}
