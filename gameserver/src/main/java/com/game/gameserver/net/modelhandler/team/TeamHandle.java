package com.game.gameserver.net.modelhandler.team;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.team.service.TeamService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.Message;
import com.game.protocol.TeamProtocol;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/17 19:39
 */
@Component
@ModuleHandler(module = ModuleKey.TEAM_MODULE)
public class TeamHandle extends BaseHandler {

    @Autowired
    private TeamService teamService;

    /**
     * 处理创建队伍
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = TeamCmd.CREATE_TEAM)
    public void handleCreateTeamReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            TeamProtocol.CreateTeamReq req = TeamProtocol.CreateTeamReq.parseFrom(message.getData());
            TeamProtocol.CreateTeamRes res = teamService.createTeam(player, req.getTeamName()
                    , req.getNum());
            Message msg = MessageUtil.createMessage(ModuleKey.TEAM_MODULE, TeamCmd.CREATE_TEAM,
                    res.toByteArray());
            channel.writeAndFlush(msg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = TeamCmd.SHOW_TEAM)
    public void handleShowTeamReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        TeamProtocol.CheckTeamRes res = teamService.checkTeamRes(player);
        Message resMsg = MessageUtil.createMessage(ModuleKey.TEAM_MODULE, TeamCmd.SHOW_TEAM, res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    @CmdHandler(cmd = TeamCmd.DISSOLVE_TEAM)
    public void handleDissolveTeamReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        teamService.dissolveTeam(player);
    }

    /**
     * 返回角色队伍
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = TeamCmd.TEAM_LIST)
    public void handleTeamListReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        TeamProtocol.TeamListRes teamList = teamService.getTeamList();
        Message res = MessageUtil.createMessage(ModuleKey.TEAM_MODULE, TeamCmd.TEAM_LIST, teamList.toByteArray());
        channel.writeAndFlush(res);
    }

    @CmdHandler(cmd = TeamCmd.ENTRY_TEAM)
    public void handleEntryTeamReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            TeamProtocol.EntryTeamReq req = TeamProtocol.EntryTeamReq.parseFrom(message.getData());
            TeamProtocol.EntryTeamRes res = teamService.entryTeam(player, req.getTeamId());
            Message resMsg = MessageUtil.createMessage(ModuleKey.TEAM_MODULE, TeamCmd.ENTRY_TEAM, res.toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = TeamCmd.EXIT_TEAM)
    public void handleExitTeamReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        TeamProtocol.ExitTeamRes res = teamService.exitTeam(player);
        Message resMsg = MessageUtil.createMessage(ModuleKey.TEAM_MODULE, TeamCmd.EXIT_TEAM, res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    @CmdHandler(cmd = TeamCmd.KICK_TEAM)
    public void handleKickTeamReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        try {
            TeamProtocol.KickTeamReq kickTeamReq = TeamProtocol.KickTeamReq.parseFrom(message.getData());
            teamService.kickTeam(player, kickTeamReq.getPlayerId());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

}
