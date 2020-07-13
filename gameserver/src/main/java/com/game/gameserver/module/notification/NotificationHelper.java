package com.game.gameserver.module.notification;

import com.game.gameserver.module.guild.domain.GuildDomain;
import com.game.gameserver.module.player.domain.PlayerDomain;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
import com.game.protocol.NotifyProtocol;
import com.game.util.MessageUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通知类 发送通知消息
 *
 * @author xuewenkang
 * @date 2020/7/9 2:22
 */
public class NotificationHelper {
    private static final Logger logger = LoggerFactory.getLogger(NotificationHelper.class);

    public static void notifyChannel(Channel channel,String content){
        NotifyProtocol.NotifyMessage  msg = NotifyProtocol.NotifyMessage.newBuilder().setMsg(content).build();
        Message message = MessageUtil.createMessage(ModuleKey.NOTIFY_MODULE,(short) 0,msg.toByteArray());
        channel.writeAndFlush(message);
    }

    public static void notifyPlayerDomain(PlayerDomain playerDomain,String content){
        NotifyProtocol.NotifyMessage  msg = NotifyProtocol.NotifyMessage.newBuilder().setMsg(content).build();
        Message message = MessageUtil.createMessage(ModuleKey.NOTIFY_MODULE,(short) 0,msg.toByteArray());
        playerDomain.getChannel().writeAndFlush(message);
    }

    public static void notifyGuild(GuildDomain guildDomain,String content){

    }

    public static void notifyScene(Scene scene,String content){

    }
}
