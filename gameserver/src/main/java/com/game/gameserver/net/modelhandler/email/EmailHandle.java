package com.game.gameserver.net.modelhandler.email;

import com.game.gameserver.module.email.entity.Email;
import com.game.gameserver.module.email.service.EmailService;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.EmailProtocol;
import com.game.protocol.Message;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/15 22:12
 */
@Component
@ModuleHandler(module = ModuleKey.EMAIL_MODULE)
public class EmailHandle extends BaseHandler {
    @Autowired
    private EmailService emailService;

    /**
     * 处理获取邮件列表请求
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = EmailCmd.EMAIL_LIST_REQ)
    public void handleEmailListReq(Message message, Channel channel) {
        PlayerObject playerObject = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerObject == null) {
            return;
        }
        EmailProtocol.EmailListRes res = emailService.getEmailList(playerObject);
        Message resMsg = MessageUtil.createMessage(ModuleKey.EMAIL_MODULE,EmailCmd.EMAIL_LIST_REQ,
                res.toByteArray());
        channel.writeAndFlush(resMsg);
    }

    /**
     * 处理删除邮件请求
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = EmailCmd.REMOVE_EMAIL_REQ)
    public void handleRemoveEmailReq(Message message,Channel channel){

    }

    /**
     * 处理发送邮件请求
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = EmailCmd.SEND_EMAIL_REQ)
    public void handleSendEmailReq(Message message,Channel channel){
        PlayerObject playerObject = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if (playerObject == null) {
            return;
        }
        try {
            EmailProtocol.SendEmailReq req = EmailProtocol.SendEmailReq.parseFrom(message.getData());
            EmailProtocol.SendEmailRes res = emailService.sendEmail(playerObject,req);
            Message resMsg = MessageUtil.createMessage(ModuleKey.EMAIL_MODULE,EmailCmd.SEND_EMAIL_REQ,
                    res.toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理查看邮件请求
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = EmailCmd.CHECK_EMAIL)
    public void handleCheckEmailReq(Message message,Channel channel){

    }

    /**
     * 处理提取附件请求
     *
     * @param message
     * @param channel
     * @return void
     */
    @CmdHandler(cmd = EmailCmd.EXTRACT_ATTACHMENTS)
    public void handleExtractAttachments(Message message,Channel channel){

    }
}
