package com.game.gameserver.module.email.service;

import com.game.gameserver.module.backbag.service.BackBagService;
import com.game.gameserver.module.email.dao.EmailDbService;
import com.game.gameserver.module.email.entity.EmailEntity;
import com.game.gameserver.module.email.helper.EmailHelper;
import com.game.gameserver.module.email.model.Email;
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
     * @param player
     * @return void
     */
    public void loadEmail(Player player) {
        // 数据库中读取数据
        List<EmailEntity> emailEntityList = emailDbService.selectEmailList(player.getPlayerEntity().getId());
        EmailBox emailBox = new EmailBox(player.getPlayerEntity().getId());
        emailEntityList.forEach(emailEntity -> {
            Email email = EmailHelper.createEmail(emailEntity);
            emailBox.getEmailMap().put(email.getId(),email);
        });
        // 放如本地缓存
        emailManager.putEmailBox(emailBox.getPlayerId(), emailBox);
    }

    /**
     * 发送邮件
     *
     * @param senderId
     * @param receiverId
     * @param title
     * @param content
     * @return boolean
     */
    public boolean sendEmail(long senderId, long receiverId, String title, String content){
        return sendEmail(senderId,receiverId,title,content,0,null);
    }

    public boolean sendEmail(long senderId, long receiverId, String title, String content,int golds){
        return sendEmail(senderId,receiverId,title,content,golds,null);
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
        // 创建一封邮件
        Email email = new Email();
        email.setId(GameUUID.getInstance().generate());
        email.setSenderId(senderId);
        email.setReceiverId(receiverId);
        email.setTitle(title);
        email.setContent(content);
        email.setGolds(golds);
        email.setState(EmailState.UNREAD);
        if(attachments==null){
            attachments = new ArrayList<>();
        }
        email.setAttachments(attachments);

        // 持久化
        int i = emailDbService.insert(email);
        if (i != 1) {
            return false;
        }
        // 查看玩家是否在线
        Player player = playerService.getPlayer(receiverId);
        if (player != null) {
            EmailBox emailBox = emailManager.getEmailBox(receiverId);
            if (emailBox != null) {
                // 将邮件放入玩家缓存中
                emailBox.getEmailMap().put(email.getId(), email);
                NotificationHelper.notifyPlayer(player, "您有一封新的邮件!");
            }
        }
        return true;
    }

    /**
     * 玩家发送邮件
     *
     * @param player
     * @param receiverId
     * @param title
     * @param content
     * @param golds
     * @param bagIndexs
     * @return void
     */
    public void sendEmailByPlayer(Player player, long receiverId, String title, String content,
                                  int golds, List<Integer> bagIndexs) {
        title = player.getPlayerEntity().getName() + ":" + title;
        // 获取玩家附件
        List<Item> attachments = new ArrayList<>();
        for (int bagIndex : bagIndexs) {
            Item item = backBagService.getItem(player, bagIndex);
            if (item != null) {
                attachments.add(item);
            }
        }
        boolean result = sendEmail(player.getPlayerEntity().getId(), receiverId, title, content, golds, attachments);
        if (!result) {
            NotificationHelper.notifyPlayer(player, "邮件发送失败");
            return;
        }
        for (Item item : attachments) {
            backBagService.removeItem(player, item.getBagIndex());
        }
        NotificationHelper.notifyPlayer(player, "邮件发送成功");
        NotificationHelper.syncBackBag(player);
    }

    /**
     * 展示邮箱
     *
     * @param player
     * @return void
     */
    public void showEmailBox(Player player) {
        EmailBox emailBox = emailManager.getEmailBox(player.getPlayerEntity().getId());
        if (emailBox == null) {
            NotificationHelper.notifyPlayer(player, "加载邮箱失败");
            return;
        }
        NotificationHelper.notifyPlayer(player, EmailHelper.buildEmailBox(emailBox));
    }

    /**
     * 查看邮件
     *
     * @param player
     * @param emailId
     * @return void
     */
    public void showEmail(Player player, long emailId) {
        EmailBox emailBox = emailManager.getEmailBox(player.getPlayerEntity().getId());
        if (emailBox == null) {
            NotificationHelper.notifyPlayer(player, "加载邮箱失败");
            return;
        }
        Email email = emailBox.getEmailMap().get(emailId);
        if (email == null) {
            NotificationHelper.notifyPlayer(player, "无此邮件");
            return;
        }
        NotificationHelper.notifyPlayer(player, EmailHelper.buildEmail(email));
        email.setState(EmailState.READ);
        emailDbService.update(email);
    }

    /**
     * 删除邮件
     *
     * @param player
     * @param emailId
     * @return void
     */
    public void deleteEmail(Player player, long emailId) {
        EmailBox emailBox = emailManager.getEmailBox(player.getPlayerEntity().getId());
        if (emailBox == null) {
            NotificationHelper.notifyPlayer(player, "加载邮箱失败");
            return;
        }
        Email email = emailBox.getEmailMap().get(emailId);
        if (email == null) {
            NotificationHelper.notifyPlayer(player, "无此邮件");
            return;
        }
        NotificationHelper.notifyPlayer(player, "删除邮件");
        emailBox.getEmailMap().remove(emailId);
        emailDbService.delete(emailId);
    }

    /**
     * 提取附件
     *
     * @param player
     * @param emailId
     * @return void
     */
    public void extractAttachments(Player player, long emailId) {
        EmailBox emailBox = emailManager.getEmailBox(player.getPlayerEntity().getId());
        if (emailBox == null) {
            NotificationHelper.notifyPlayer(player, "加载邮箱失败");
            return;
        }
        Email email = emailBox.getEmailMap().get(emailId);
        if (email == null) {
            NotificationHelper.notifyPlayer(player, "无此邮件");
            return;
        }
        // 提取金币
        player.addGolds(email.getGolds());
        // 提取附件 如果该附件道具能够放进背包 则移除
        email.getAttachments().removeIf(
                item -> backBagService.addItem(player, item));
        // 更新邮件信息
        emailDbService.update(email);
        NotificationHelper.syncBackBag(player);
        NotificationHelper.notifyPlayer(player, "提取附件成功");
    }

}
