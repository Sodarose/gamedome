package com.game.gameserver.module.chat.service;

import com.game.gameserver.module.chat.type.ChannelType;
import com.game.gameserver.module.guild.model.Guild;
import com.game.gameserver.module.guild.service.GuildService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.scene.model.GameScene;
import com.game.gameserver.module.team.model.Team;
import com.game.gameserver.module.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author xuewenkang
 * @date 2020/7/13 22:02
 */
@Service
public class ChatService {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private GuildService guildService;
    @Autowired
    private TeamService teamService;

    /**
     * 私聊
     *
     * @param player
     * @param targetId
     * @param content
     * @return void
     */
    public void privateChat(Player player, long targetId, String content) {
        // 获取目标用户
        Player target = playerService.getPlayer(targetId);
        if (target == null) {
            NotificationHelper.notifyPlayer(player,"该玩家未在线");
            return;
        }
        // 发送私聊消息消息
        NotificationHelper.notifyPlayer(target, MessageFormat
                .format("{0}:{1}",player.getName(),content));
    }

    /**
     * 本地聊天
     *
     * @param player
     * @param content
     * @return void
     */
    public void localChat(Player player, String content) {
        GameScene currScene = (GameScene) player.getCurrScene();
        if(currScene==null){
            NotificationHelper.notifyPlayer(player,"未知场景");
            return;
        }
        NotificationHelper.notifyScene(currScene,MessageFormat
                .format("{0}:{1}",
                player.getName(),content));
    }

    /**
     * 频道聊天
     *
     * @param player
     * @param channelType
     * @param content
     * @return void
     */
    public void channelChat(Player player, int channelType, String content) {
        switch (channelType){
            case ChannelType.WORLD_CHANNEL:
                worldChat(player,content);
                break;
            case ChannelType.GUILD_CHANNEL:
                // 发送到公会频道
                guildChat(player,content);
                break;
            case ChannelType.TEAM_CHANNEL:
                // 发送到组队频道
                teamChat(player,content);
                break;
            default:{}
        }
    }

    /**
     * 世界聊天
     *
     * @param player
     * @param content
     * @return void
     */
    private void worldChat(Player player,String content){
        playerService.getAllPlayer().forEach((key,value)->{
            NotificationHelper.notifyPlayer(value,MessageFormat.format("(世界){0}:{1}",
                    player.getName(),content));
        });
    }

    /**
     * 公会聊天
     *
     * @param player
     * @param content
     * @return void
     */
    private void guildChat(Player player, String content){
        Guild guildDomain = guildService.getGuildDomain(player.getPlayerEntity().getGuildId());
        if(guildDomain==null){
            return;
        }
        // 发送该还在线的公会成员
        guildDomain.getMemberMap().forEach((key,value)->{
            Player target = playerService.getPlayer(value.getPlayerId());
            if(target!=null){
                NotificationHelper.notifyPlayer(target,
                        MessageFormat.format("(公会){0}:{1}",player.getName(),content));
            }
        });
    }

    /**
     * 组队聊天
     *
     * @param player
     * @param content
     * @return void
     */
    private void teamChat(Player player, String content){
        Team team = teamService.getTeam(player.getTeamId());
        if(team==null){
            return;
        }
        // 发送和消息给队伍任务
        team.getMemberMap().forEach((key,value)->{
            NotificationHelper.notifyPlayer(value,MessageFormat.format("(队伍){0}:{1}",
                    player.getName(),content));
        });
    }
}
