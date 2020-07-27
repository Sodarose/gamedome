package com.game.gameserver.net.modelhandler.email;

import com.game.gameserver.module.email.service.EmailService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangkang
 */
@Component
@ModuleHandler(module = ModuleKey.EMAIL_MODULE)
public class EmailHandle extends BaseHandler {

    @Autowired
    private EmailService emailService;

    @CmdHandler(cmd = EmailCmd.SHOW_EMAIL_BOX)
    public void showEmailBox(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        emailService.showEmailBox(player);
    }

    @CmdHandler(cmd = EmailCmd.SHOW_EMAIL)
    public void showEmail(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        long emailId = Long.parseLong(message.getContent());
        emailService.showEmail(player,emailId);
    }

    @CmdHandler(cmd = EmailCmd.SEND_EMAIL)
    public void sendEmail(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        String[] param = message.getContent().split("\\s+");
        long receiverId = Long.parseLong(param[0]);
        String title = param[1];
        String content = param[2];
        int golds = Integer.parseInt(param[3]);
        List<Integer> bagIndexs = new ArrayList<>();
        for(int i=4;i<param.length;i++){
            bagIndexs.add(Integer.parseInt(param[i]));
        }
        emailService.sendEmailByPlayer(player,receiverId,title,content,golds,bagIndexs);
    }

    @CmdHandler(cmd = EmailCmd.DELETE_EMAIL)
    public void deleteEmail(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        long emailId = Long.parseLong(message.getContent());
        emailService.deleteEmail(player,emailId);
    }

    @CmdHandler(cmd = EmailCmd.EXTRACT_ATTACH)
    public void extractAttach(Message message,Channel channel){
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (player == null) {
            NotificationHelper.notifyChannel(channel, "请先登录角色");
            return;
        }
        long emailId = Long.parseLong(message.getContent());
        emailService.extractAttachments(player,emailId);
    }
}
