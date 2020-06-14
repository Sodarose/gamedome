package com.game.gameserver.util;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.protocol.PlayerProtocol;

import java.util.List;

/**
 * 转换工具
 *
 * @author xuewenkang
 * @date 2020/5/25 13:15
 */
public class ProtocolFactory {

    /**
     * 将playerList 转换为 PlayerProtocol.PlayerList
     *
     * @param playerList
     * @return com.game.protocol.PlayerProtocol.PlayerList
     */
    public static PlayerProtocol.PlayerListRes createPlayerList(List<Player> playerList){
        PlayerProtocol.PlayerListRes.Builder builder = PlayerProtocol.PlayerListRes.newBuilder();
        for(Player player:playerList){
            builder.addPlayerInfoList(createSimplePlayerInfo(player));
        }
        return builder.build();
    }

    /**
     * 将player 转换为 PlayerProtocol.BriefPlayerInfo
     *
     * @param player
     * @return com.game.protocol.PlayerProtocol.BriefPlayerInfo
     */
    public static PlayerProtocol.SimplePlayerInfo createSimplePlayerInfo(Player player){
        PlayerProtocol.SimplePlayerInfo.Builder builder = PlayerProtocol.SimplePlayerInfo.newBuilder();
        builder.setId(player.getId());
        builder.setName(player.getName());
        builder.setCareerId(player.getCareerId());
        builder.setLevel(player.getLevel());
        return builder.build();
    }

    public static PlayerProtocol.LoginPlayerRes createLoginPlayerRes(int code,String msg,PlayerObject playerObject){
        PlayerProtocol.LoginPlayerRes.Builder builder = PlayerProtocol.LoginPlayerRes.newBuilder();
        builder.setCode(code);
        builder.setMsg(msg);
        if(playerObject!=null){
            builder.setPlayerInfo(createPlayerInfo(playerObject));
        }
        return builder.build();
    }

    public static PlayerProtocol.PlayerInfo createPlayerInfo(PlayerObject playerObject){
        return null;
    }
}
