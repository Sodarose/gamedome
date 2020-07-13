package com.game.gameserver.net.modelhandler.guild;

import com.game.gameserver.module.guild.service.GuildService;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
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
 * @date 2020/7/8 15:38
 */
@Component
@ModuleHandler(module = ModuleKey.GUILD_MODULE)
public class GuildHandle extends BaseHandler {

    @Autowired
    private GuildService guildService;

    @CmdHandler(cmd = GuildCmd.CREATE_GUILD)
    public void handleCreateGuildReq(Message message, Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            return;
        }
        String guildNme = message.getContent();
        guildService.createGuild(playerDomain,guildNme);
    }

    @CmdHandler(cmd = GuildCmd.GUILD_LIST)
    public void handleGuidListReq(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
       guildService.checkGuildList();
    }

    @CmdHandler(cmd = GuildCmd.CHECK_GUILD)
    public void handleCheckGuildReq(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        guildService.checkGuild(playerDomain);
    }

    @CmdHandler(cmd = GuildCmd.APPLY_GUILD)
    public void handleApplyGuildReq(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        long guildId = Long.parseLong(message.getContent());
        guildService.applyGuild(playerDomain,guildId);
    }

    @CmdHandler(cmd = GuildCmd.PROCESS_APPLY)
    public void handleProcessApply(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        //guildService.processApplyInfo(playerDomain,);
    }

    @CmdHandler(cmd = GuildCmd.APPOINT)
    public void handleAppointReq(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        String applyName = param[0];
        int position = Integer.parseInt(param[1]);
        guildService.appointPosition(playerDomain,applyName,position);
    }

    @CmdHandler(cmd = GuildCmd.DONATE_GOLDS)
    public void handleDonateGoldsReq(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerDomain == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int golds = Integer.parseInt(message.getContent());
        guildService.donateGold(playerDomain,golds);
    }

    @CmdHandler(cmd = GuildCmd.SHOW_GUILD_W)
    public void handleShowGuildWReq(Message message,Channel channel){

    }
}
