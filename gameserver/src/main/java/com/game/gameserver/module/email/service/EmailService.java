package com.game.gameserver.module.email.service;

import com.game.gameserver.module.email.dao.EmailDbService;
import com.game.gameserver.module.email.entity.EmailEntity;
import com.game.gameserver.module.email.model.EmailBox;
import com.game.gameserver.module.email.manager.EmailManager;
import com.game.gameserver.module.player.domain.PlayerDomain;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.util.ProtocolFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 邮件服务
 *
 * @author xuewenkang
 * @date 2020/6/15 20:42*/

@Service
public class EmailServiceImpl {
    @Autowired
    private EmailManager emailManager;

    @Autowired
    private EmailDbService emailDbService;
    /**
     * 加载用户邮箱
     *
     * @param playerDomain
     * @return void
     */
    public void loadEmail(PlayerDomain playerDomain){
        List<EmailEntity> emailEntityList =
    }

    public void showEmailBox(PlayerDomain playerDomain){

    }

/**
     * 获得邮件列表
     *
     * @param player
     * @return com.game.protocol.EmailProtocol.EmailListRes*//*

    @Override
    public EmailProtocol.EmailListRes getEmailList(Player player) {
        EmailBox emailBox = emailManager
                .getEmailBox(player.getId());
        return ProtocolFactory.createEmailListRes(0,"success",emailBox.getEmailList());
    }

*
     * 发送邮件
     *
     * @param player
     * @param req
     * @return com.game.protocol.EmailProtocol.SendEmailRes


    @Override
    public EmailProtocol.SendEmailRes sendEmail(Player player, EmailProtocol.SendEmailReq req) {
        Player targetObject = playerManager.getPlayer(req.getReceiverId());
        // 对方不在线
        if(targetObject==null){
            return sendEmailByOffLine(player,req);
        }
        EmailBox emailBox = emailManager.getEmailBox(req.getReceiverId());
        if(emailBox==null){
            return EmailProtocol.SendEmailRes.newBuilder().setCode(1001).setMsg("数据错误").build();
        }
        // 创建邮件
        Email email = new Email(req.getTitle(), player.getId(),
                player.getName(),req.getContent());
        email.setPlayerId(req.getReceiverId());
        // 写入金币
        if(player.getGolds()<req.getGolds()){
            return EmailProtocol.SendEmailRes.newBuilder().setCode(1002).setMsg("金币不足").build();
        }
        email.setGolds(req.getGolds());
        // 写入道具
List<Item> items = new ArrayList<>();
        for(EmailProtocol.EmailAttachment attachment:req.getAttachmentList()){
            Item item = itemManager.getItemInPlayerBag(player.getId(),
                    attachment.getItemId());
            items.add(item);
        }
        email.setAttachments(items);
        // 减少金币
        player.setGolds(player.getGolds()-req.getGolds());
        // 删除道具
        items.forEach(item -> {
            itemManager.removeItemInPlayerBag(player.getId(),item.getId());
        });

        // 投递邮件
        emailBox.addEmail(email);
        return EmailProtocol.SendEmailRes.newBuilder().setCode(1002).setMsg("发送成功").build();
    }

    private EmailProtocol.SendEmailRes sendEmailByOffLine(Player player, EmailProtocol.SendEmailReq req){
        return null;
    }*/
}
