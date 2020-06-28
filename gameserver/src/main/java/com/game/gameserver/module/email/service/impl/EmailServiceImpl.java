package com.game.gameserver.module.email.service.impl;

import com.game.gameserver.module.email.entity.Email;
import com.game.gameserver.module.email.entity.EmailBox;
import com.game.gameserver.module.email.manager.EmailManager;
import com.game.gameserver.module.email.service.EmailService;
import com.game.gameserver.module.item.entity.Bag;
import com.game.gameserver.module.item.entity.Item;
import com.game.gameserver.module.item.manager.ItemManager;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.EmailProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件服务
 *
 * @author xuewenkang
 * @date 2020/6/15 20:42
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailManager emailManager;
    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private ItemManager itemManager;

    /**
     * 获得邮件列表
     *
     * @param playerObject
     * @return com.game.protocol.EmailProtocol.EmailListRes
     */
    @Override
    public EmailProtocol.EmailListRes getEmailList(PlayerObject playerObject) {
        EmailBox emailBox = emailManager
                .getEmailBox(playerObject.getPlayer().getId());
        return ProtocolFactory.createEmailListRes(0,"success",emailBox.getEmailList());
    }

    /**
     * 发送邮件
     *
     * @param playerObject
     * @param req
     * @return com.game.protocol.EmailProtocol.SendEmailRes
     */
    @Override
    public EmailProtocol.SendEmailRes sendEmail(PlayerObject playerObject, EmailProtocol.SendEmailReq req) {
        PlayerObject targetObject = playerManager.getPlayerObject(req.getReceiverId());
        // 对方不在线
        if(targetObject==null){
            return sendEmailByOffLine(playerObject,req);
        }
        EmailBox emailBox = emailManager.getEmailBox(req.getReceiverId());
        if(emailBox==null){
            return EmailProtocol.SendEmailRes.newBuilder().setCode(1001).setMsg("数据错误").build();
        }
        // 创建邮件
        Email email = new Email(req.getTitle(),playerObject.getPlayer().getId(),
                playerObject.getPlayer().getName(),req.getContent());
        email.setPlayerId(req.getReceiverId());
        // 写入金币
        if(playerObject.getPlayer().getGolds()<req.getGolds()){
            return EmailProtocol.SendEmailRes.newBuilder().setCode(1002).setMsg("金币不足").build();
        }
        email.setGolds(req.getGolds());
        // 写入道具
        List<Item> items = new ArrayList<>();
        for(EmailProtocol.EmailAttachment attachment:req.getAttachmentList()){
            Item item = itemManager.getItemInPlayerBag(playerObject.getPlayer().getId(),
                    attachment.getItemId());
            items.add(item);
        }
        email.setAttachments(items);
        // 减少金币
        playerObject.getPlayer().setGolds(playerObject.getPlayer().getGolds()-req.getGolds());
        // 删除道具
        items.forEach(item -> {
            itemManager.removeItemInPlayerBag(playerObject.getPlayer().getId(),item.getId());
        });
        // 投递邮件
        emailBox.addEmail(email);
        return EmailProtocol.SendEmailRes.newBuilder().setCode(1002).setMsg("发送成功").build();
    }

    private EmailProtocol.SendEmailRes sendEmailByOffLine(PlayerObject playerObject, EmailProtocol.SendEmailReq req){
        return null;
    }
}
