package com.game.gameserver.module.team.helper;

import com.game.gameserver.module.team.model.Team;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/13 14:06
 */
public class TeamHelper {

    public static String buildTeamListMsg(List<Team> teams){
        StringBuilder sb = new StringBuilder();
        for(Team team:teams){
            sb.append(buildTeamMsg(team));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String buildTeamMsg(Team team){
        StringBuilder sb = new StringBuilder("队伍信息:").append("\n");
        sb.append("id:").append(team.getId()).append("\n");
        sb.append("name:").append(team.getTeamName()).append("\n");
        sb.append("capacity:").append(team.getMemberMap().size()).append("/").append(team.getCapacity()).append("\n");
        sb.append("member:").append("\n");
        team.getMemberMap().forEach((key,value)->{
            sb.append(value.getPlayerEntity().getName()).append("(")
                    .append(value.getPlayerBattle().getHp())
                    .append(")").append("\t");
        });
        sb.append("\n");
        sb.append("申请人列表:").append(team.getApply().toString()).append("\n");
        sb.append("邀请人列表:").append(team.getInvite().toString()).append("\n");
        return sb.toString();
    }
}
