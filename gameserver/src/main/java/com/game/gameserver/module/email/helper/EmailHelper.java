package com.game.gameserver.module.email.helper;

import com.alibaba.fastjson.JSON;
import com.game.gameserver.module.email.entity.EmailEntity;
import com.game.gameserver.module.email.model.Email;
import com.game.gameserver.module.email.model.EmailBox;
import com.game.gameserver.module.email.type.EmailState;
import com.game.gameserver.module.item.helper.ItemHelper;
import com.game.gameserver.module.item.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/13 2:17
 */
public class EmailHelper {
    public static String buildEmailBox(EmailBox emailBox){
        StringBuilder sb = new StringBuilder("邮箱:");
        if(emailBox.getEmailMap().size()==0){
            sb.append("空空如也");
            return sb.toString();
        }
        emailBox.getEmailMap().forEach((key,value)->{
            sb.append("id:").append(value.getId()).append("\n");
            sb.append("标题:").append(value.getTitle()).append("\n");
            sb.append("\n");
        });
        return sb.toString();
    }

    /**
     *
     *
     * @param email
     * @return java.lang.String
     */
    public static String buildEmail(Email email){
        StringBuilder sb = new StringBuilder("邮件:");
        sb.append("id:").append(email.getId()).append("\n");
        sb.append("title:").append(email.getTitle()).append("\n");
        sb.append("senderId:").append(email.getSenderId()).append("\n");
        sb.append("content:").append(email.getContent()).append("\n");
        sb.append("attachments:").append("\n");
        for(Item item : email.getAttachments()){
            sb.append(ItemHelper.buildItemSimpleMsg(item)).append("\t");
        }
        sb.append("golds:").append(email.getGolds()).append("\n");
        sb.append("state:").append(email.getState().equals(EmailState.READ)?"已读":"未读").append("\n");
        return sb.toString();
    }

    public static Email createEmail(EmailEntity emailEntity){
        Email email = new Email();
        email.setId(emailEntity.getId());
        email.setTitle(emailEntity.getTitle());
        email.setSenderId(emailEntity.getSenderId());
        email.setReceiverId(emailEntity.getReceiverId());
        email.setContent(emailEntity.getContent());
        email.setGolds(emailEntity.getGolds());
        email.setState(emailEntity.getState());
        List<Item> attachments = JSON.parseArray(emailEntity.getAttachments(),Item.class);
        if(attachments==null){
            attachments = new ArrayList<>();
        }
        email.setAttachments(attachments);
        return email;
    }

    public static EmailEntity createEmailEntity(Email email){
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setId(email.getId());
        emailEntity.setTitle(email.getTitle());
        emailEntity.setSenderId(email.getSenderId());
        emailEntity.setReceiverId(email.getReceiverId());
        emailEntity.setContent(email.getContent());
        emailEntity.setGolds(email.getGolds());
        emailEntity.setState(email.getState());
        String attachments = JSON.toJSONString(email.getAttachments());
        emailEntity.setAttachments(attachments);
        return emailEntity;
    }
}
