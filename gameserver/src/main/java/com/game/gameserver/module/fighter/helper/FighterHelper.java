package com.game.gameserver.module.fighter.helper;

import com.game.gameserver.common.config.ItemConfig;
import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.config.StaticConfigManager;

/**
 * @author xuewenkang
 * @date 2020/7/23 21:22
 */
public class FighterHelper {
    public static String buildMonsterAwardMsg(MonsterConfig monsterConfig){
        StringBuilder sb = new StringBuilder();
        sb.append("获得经验:").append(monsterConfig.getExprAward()).append("\n");
        sb.append("获得金币:").append(monsterConfig.getGoldAward()).append("\n");
        sb.append("获得道具:").append("\n");
        monsterConfig.getItemAward().forEach(award -> {
            ItemConfig itemConfig = StaticConfigManager.getInstance().getItemConfigMap().get(award.getItemId());
            if(itemConfig!=null){
                sb.append(itemConfig.getName()).append(" x ").append(award.getNum()).append("\n");
            }
        });
        return sb.toString();
    }
}
