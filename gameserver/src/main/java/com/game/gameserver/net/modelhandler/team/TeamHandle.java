package com.game.gameserver.net.modelhandler.team;

import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.team.service.TeamService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
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

    @CmdHandler(cmd = TeamCmd.CREATE_TEAM)
    public void createTeam(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        String teamName = param[0];
        int capacity = Integer.parseInt(param[1]);
        teamService.createTeam(player,teamName,capacity);
    }

    @CmdHandler(cmd = TeamCmd.SHOW_TEAM)
    public void showTeam(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        teamService.showTeam(player);
    }

    @CmdHandler(cmd = TeamCmd.SHOW_TEAM_LIST)
    public void showTeamList(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        teamService.showTeamList(player);
    }

    @CmdHandler(cmd = TeamCmd.APPLY_FOR_TEAM)
    public void applyForTeam(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        long teamId = Long.parseLong(message.getContent());
        teamService.applyForItem(player,teamId);
    }

    @CmdHandler(cmd = TeamCmd.INVITE_TEAM)
    public void inviteTeam(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        long targetId = Long.parseLong(message.getContent());
        teamService.inviteTeam(player,targetId);
    }

    @CmdHandler(cmd = TeamCmd.PROCESS_APPLY)
    public void processApply(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        long targetId = Long.parseLong(param[0]);
        int agree = Integer.parseInt(param[1]);
        teamService.processApply(player,targetId,agree);
    }

    @CmdHandler(cmd = TeamCmd.PROCESS_INVITE)
    public void processInvite(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        long itemId = Long.parseLong(param[0]);
        int agree = Integer.parseInt(param[1]);
        teamService.processInvite(player,itemId,agree);
    }

    @CmdHandler(cmd = TeamCmd.EXIT_TEAM)
    public void exitTeam(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        teamService.exitItem(player);
    }

    @CmdHandler(cmd = TeamCmd.DISSOLVE_TEAM)
    public void dissolveTeam(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        teamService.dissolveTeam(player);
    }

}
