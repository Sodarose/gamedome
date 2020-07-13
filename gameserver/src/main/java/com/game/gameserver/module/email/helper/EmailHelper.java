package com.game.gameserver.module.email.helper;

import com.game.gameserver.module.email.entity.EmailEntity;
import com.game.gameserver.module.email.model.EmailBox;
import com.game.gameserver.module.email.type.EmailState;

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
     * @param emailEntity
     * @return java.lang.String
     */
    public static String buildEmail(EmailEntity emailEntity){
        StringBuilder sb = new StringBuilder("邮件:");
        sb.append("id:").append(emailEntity.getId()).append("\n");
        sb.append("title:").append(emailEntity.getTitle()).append("\n");
        sb.append("senderId:").append(emailEntity.getSenderId()).append("\n");
        sb.append("content:").append(emailEntity.getContent()).append("\n");
        sb.append("attachments:").append(emailEntity.getAttachments()).append("\n");
        sb.append("golds:").append(emailEntity.getGolds()).append("\n");
        sb.append("state:").append(emailEntity.getState().equals(EmailState.READ)?"已读":"未读").append("\n");
        return sb.toString();
    }
}
