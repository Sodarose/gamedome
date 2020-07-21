package com.game.gameserver.module.monster.helper;

import com.game.gameserver.common.fsm.state.monster.MonsterState;
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
        sb.append("state:").append(monster.getState().name()).append("\n");
        sb.append("HP:").append(monster.getCurrHp()).append("/").append(monster.getHp()).append("\n");
        sb.append("MP:").append(monster.getCurrHp()).append("/").append(monster.getMp()).append("\n");
        sb.append("ATTACK:").append(monster.getAttack()).append("\n");
        sb.append("DEFENSE:").append(monster.getDefense()).append("\n");
        return sb.toString();
    }

    public static String buildStateMsg(Monster monster){
        if(monster.getState()== MonsterState.MONSTER_PATROL){
            return "巡逻";
        }
        if(monster.getState()== MonsterState.MONSTER_DEFEND){
            return "驻守";
        }
        if(monster.getState()== MonsterState.MONSTER_ATTACK){
            return "攻击";
        }
        if(monster.getState()== MonsterState.MONSTER_TAKEOFF){
            return "脱战";
        }
        if(monster.getState()== MonsterState.MONSTER_DEAD){
            return "死亡";
        }
        return "";
    }
}
