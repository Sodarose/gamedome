package com.game.gameserver.module.backbag.helper;


import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.backbag.model.BackBag;
import com.game.gameserver.module.item.model.Item;

/**
 * 物品辅助类 用于同步数据以及构建协议
 *
 * @author xuewenkang
 * @date 2020/7/7 3:31
 */
public class BackBagHelper {
    public static String buildPlayerBackBag(BackBag backBag) {
        StringBuilder sb = new StringBuilder("背包信息:").append("\n");
        for (int i = 0; i < backBag.getCapacity(); i++) {
            Item item = backBag.getItemMap().get(i);
            sb.append("[").append(item == null ? "空" : buildSimpleItemMsg(item))
                    .append("]").append("\t");
            if (i != 0 && (i + 1) % 6 == 0) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static String buildSimpleItemMsg(Item item){
        ItemConfig itemConfig = StaticConfigManager.getInstance()
                .getItemConfigMap().get(item.getItemConfigId());
        return itemConfig.getName() + "(" + item.getNum() + ")";
    }
}
