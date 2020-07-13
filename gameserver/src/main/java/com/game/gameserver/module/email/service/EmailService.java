package com.game.gameserver.module.email.service;

import com.alibaba.fastjson.JSON;
import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.email.dao.EmailDbService;
import com.game.gameserver.module.email.entity.EmailEntity;
import com.game.gameserver.module.email.helper.EmailHelper;
import com.game.gameserver.module.email.model.EmailBox;
import com.game.gameserver.module.email.manager.EmailManager;
import com.game.gameserver.module.email.type.EmailState;
import com.game.gameserver.module.item.model.Item;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.util.GameUUID;
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
public class EmailService {
    @Autowired
    private EmailManager emailManager;

    @Autowired
    private EmailDbService emailDbService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private BackBagService backBagService;

    /**
     * 加载用户邮箱
     *
     * @param playerDomain
     * @return void
     */
    public void loadEmail(Player playerDomain) {
        // 数据库中读取数据
        List<EmailEntity> emailEntityList = emailDbService.selectEmailList(playerDomain.getPlayerEntity().getId());
        EmailBox emailBox = new EmailBox(playerDomain.getPlayerEntity().getId());
        emailBox.initialize(emailEntityList);
        // 放如本地缓存
        emailManager.putEmailBox(emailBox.getPlayerId(), emailBox);
    }

    public boolean sendEmail(long senderId, long receiverId, String title, String content){
        return sendEmail(senderId,receiverId,title,content,0,null);
    }

    /**
     * 发送邮件
     *
     * @param senderId
     * @param receiverId
     * @param title
     * @param content
     * @param attachments
     * @return void
     */
    public boolean sendEmail(long senderId, long receiverId, String title, String content, int golds,
                             List<Item> attachments) {
        if (golds < 0) {
            return false;
        }
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setId(GameUUID.getInstance().generate());
        emailEntity.setSenderId(senderId);
        emailEntity.setReceiverId(receiverId);
        emailEntity.setTitle(title);
        emailEntity.setContent(content);
        emailEntity.setGolds(golds);
        String attachmentsJson = JSON.toJSONString(attachments);
        emailEntity.setAttachments(attachmentsJson);
        emailEntity.setState(EmailState.UNREAD);

        // 持久化
        int i = emailDbService.insert(emailEntity);
        if (i != 1) {
            return false;
        }
        // 查看玩家是否在线
        Player playerDomain = playerService.getPlayer(receiverId);
        if (playerDomain != null) {
            EmailBox emailBox = emailManager.getEmailBox(receiverId);
            if (emailBox != null) {
                // 将邮件放入玩家缓存中
                emailBox.getEmailMap().put(emailEntity.getId(), emailEntity);
                NotificationHelper.notifyPlayer(playerDomain, "您有一封新的邮件!");
            }
        }
        return true;
    }

    /**
     * 玩家发送邮件
     *
     * @param playerDomain
     * @param receiverId
     * @param title
     * @param content
     * @param golds
     * @param bagIndexs
     * @return void
     */
    public void sendEmailByPlayer(Player playerDomain, long receiverId, String title, String content,
                                  int golds, List<Integer> bagIndexs) {
        title = playerDomain.getPlayerEntity().getName() + ":" + title;
        // 获取玩家附件
        List<Item> attachments = new ArrayList<>();
        for (int bagIndex : bagIndexs) {
            Item item = backBagService.getItem(playerDomain, bagIndex);
            if (item != null) {
                attachments.add(item);
            }
        }
        boolean result = sendEmail(playerDomain.getPlayerEntity().getId(), receiverId, title, content, golds, attachments);
        if (!result) {
            NotificationHelper.notifyPlayer(playerDomain, "邮件发送失败");
            return;
        }
        for (Item item : attachments) {
            backBagService.removeItem(playerDomain, item.getBagIndex());
        }
        NotificationHelper.notifyPlayer(playerDomain, "邮件发送成功");
    }

    /**
     * 展示邮箱
     *
     * @param playerDomain
     * @return void
     */
    public void showEmailBox(Player playerDomain) {
        EmailBox emailBox = emailManager.getEmailBox(playerDomain.getPlayerEntity().getId());
        if (emailBox == null) {
            NotificationHelper.notifyPlayer(playerDomain, "加载邮箱失败");
            return;
        }
        NotificationHelper.notifyPlayer(playerDomain, EmailHelper.buildEmailBox(emailBox));
    }

    public void showEmail(Player playerDomain, long emailId) {
        EmailBox emailBox = emailManager.getEmailBox(playerDomain.getPlayerEntity().getId());
        if (emailBox == null) {
            NotificationHelper.notifyPlayer(playerDomain, "加载邮箱失败");
            return;
        }
        EmailEntity emailEntity = emailBox.getEmailMap().get(emailId);
        if (emailEntity == null) {
            NotificationHelper.notifyPlayer(playerDomain, "无此邮件");
            return;
        }
        NotificationHelper.notifyPlayer(playerDomain, EmailHelper.buildEmail(emailEntity));
        emailEntity.setState(EmailState.READ);
        emailDbService.update(emailEntity);
    }

    public void deleteEmail(Player playerDomain, long emailId) {
        EmailBox emailBox = emailManager.getEmailBox(playerDomain.getPlayerEntity().getId());
        if (emailBox == null) {
            NotificationHelper.notifyPlayer(playerDomain, "加载邮箱失败");
            return;
        }
        EmailEntity emailEntity = emailBox.getEmailMap().get(emailId);
        if (emailEntity == null) {
            NotificationHelper.notifyPlayer(playerDomain, "无此邮件");
            return;
        }
        NotificationHelper.notifyPlayer(playerDomain, "删除邮件");
        emailBox.getEmailMap().remove(emailId);
        emailDbService.delete(emailId);
    }

    public void extractAttachments(Player playerDomain, long emailId) {
        EmailBox emailBox = emailManager.getEmailBox(playerDomain.getPlayerEntity().getId());
        if (emailBox == null) {
            NotificationHelper.notifyPlayer(playerDomain, "加载邮箱失败");
            return;
        }
        EmailEntity emailEntity = emailBox.getEmailMap().get(emailId);
        if (emailEntity == null) {
            NotificationHelper.notifyPlayer(playerDomain, "无此邮件");
            return;
        }
        // 提取附件
        int golds = emailEntity.getGolds();
        String attachments = emailEntity.getAttachments();
        List<Item> items = JSON.parseArray(attachments, Item.class);
        // 先进行更新
        emailEntity.setGolds(0);
        emailEntity.setAttachments("");
        int i = emailDbService.update(emailEntity);
        if (i != 1) {
            emailEntity.setGolds(golds);
            emailEntity.setAttachments(attachments);
            NotificationHelper.notifyPlayer(playerDomain, "提取附件失败");
            return;
        }
        playerDomain.getPlayerEntity().setGolds(playerDomain.getPlayerEntity().getGolds() + golds);
        if (items != null) {
            for (Item item : items) {
                backBagService.addItem(playerDomain, item);
            }
        }

        NotificationHelper.notifyPlayer(playerDomain, "提取附件成功");
    }

}
