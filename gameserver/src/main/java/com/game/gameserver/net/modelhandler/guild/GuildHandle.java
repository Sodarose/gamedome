package com.game.gameserver.net.modelhandler.guild;

import com.game.gameserver.module.guild.service.GuildService;
import com.game.gameserver.module.guild.service.GuildWarehouseService;
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
    @Autowired
    private GuildWarehouseService guildWarehouseService;

    @CmdHandler(cmd = GuildCmd.CREATE_GUILD)
    public void createGuild(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            return;
        }
        String guildNme = message.getContent();
        guildService.createGuild(player,guildNme);
    }

    @CmdHandler(cmd = GuildCmd.SHOW_GUILD_LIST)
    public void showGuildList(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
       guildService.showGuildList(player);
    }

    @CmdHandler(cmd = GuildCmd.SHOW_GUILD)
    public void showGuild(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        guildService.showGuild(player);
    }

    @CmdHandler(cmd = GuildCmd.APPLY_FOR_GUILD)
    public void applyForGuild(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        long guildId = Long.parseLong(message.getContent());
        guildService.applyGuild(player,guildId);
    }

    @CmdHandler(cmd = GuildCmd.PROCESS_GUILD_APPLY)
    public void processApply(Message message, Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        String applicantName = param[0];
        int agree = Integer.parseInt(param[1]);
        guildService.processApplicant(player,applicantName,agree);
    }

    @CmdHandler(cmd = GuildCmd.APPOINT)
    public void appoint(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        String applyName = param[0];
        int position = Integer.parseInt(param[1]);
        guildService.appointPosition(player,applyName,position);
    }

    @CmdHandler(cmd = GuildCmd.DONATE_GOLDS)
    public void donateGolds(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int golds = Integer.parseInt(message.getContent());
        guildService.donateGold(player,golds);
    }

    @CmdHandler(cmd = GuildCmd.SHOW_GUILD_W)
    public void showGuildWarehouse(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        guildWarehouseService.showGuildWarehouse(player);
    }

    @CmdHandler(cmd = GuildCmd.CLEAR_UP_W)
    public void clearUpGuildWarehouse(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        guildWarehouseService.clearUpWarehouse(player);
    }

    @CmdHandler(cmd = GuildCmd.PUTIN_GUILD_W)
    public void putInGuildWarehouse(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int bagIndex = Integer.parseInt(message.getContent());
        guildWarehouseService.putInItem2GuildWarehouse(player,bagIndex);
        NotificationHelper.syncBackBag(player);
    }

    @CmdHandler(cmd = GuildCmd.TAKEOUT_GUILD_W)
    public void takeOutWarehouse(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        int bagIndex = Integer.parseInt(message.getContent());
        guildWarehouseService.takeOutItem2BackBag(player,bagIndex);
        NotificationHelper.syncBackBag(player);
    }

    @CmdHandler(cmd = GuildCmd.EXIT_GUILD)
    public void exitGuild(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        guildService.exitGuild(player);
    }

}
