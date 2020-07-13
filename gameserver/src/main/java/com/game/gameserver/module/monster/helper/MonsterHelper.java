package com.game.gameserver.module.monster.helper;

import com.game.gameserver.module.monster.model.Monster;

/**
 * @author xuewenkang
 * @date 2020/7/11 20:00
 */
public class MonsterHelper {
    public static String buildMonsterMsg(Monster monster){
        StringBuilder sb = new StringBuilder();
        sb.append("id:").append("").append("\n");
        sb.append("name:").append(monster.getName()).append("\n");
        sb.append("level:").append(monster.getLevel()).append("\n");
        sb.append("HP:").append(monster.getCurrHp()).append("/").append(monster.getHp()).append("\n");
        sb.append("MP:").append(monster.getCurrHp()).append("/").append(monster.getMp()).append("\n");
        sb.append("ATTACK:").append(monster.getAttack()).append("\n");
        sb.append("DEFENSE:").append(monster.getDefense()).append("\n");
        return sb.toString();
    }
}
