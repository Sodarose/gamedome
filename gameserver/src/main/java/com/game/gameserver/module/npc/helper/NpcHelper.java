package com.game.gameserver.module.npc.helper;

import com.game.gameserver.module.npc.model.Npc;

/**
 * @author xuewenkang
 * @date 2020/7/11 19:59
 */
public class NpcHelper {
    public static String buildNpcMsg(Npc npc){
        StringBuilder sb = new StringBuilder();
        sb.append("id:").append(npc.getNpcId()).append("\n");
        sb.append("name:").append(npc.getName()).append("\n");
        sb.append("level:").append(npc.getLevel()).append("\n");
        return sb.toString();
    }
}
