package com.game.module.emai;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.WordPage;
import com.game.module.item.ItemHandle;
import com.game.protocol.EmailProtocol;
import com.game.protocol.ItemProtocol;
import com.game.protocol.Message;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/17 10:00
 */
@Component
@ModuleHandler(module = ModuleKey.EMAIL_MODULE)
public class EmailHandle extends BaseHandler {
    public Map<Long, EmailProtocol.EmailInfo> emailInfoMap = new HashMap<>();

    @Autowired
    private ClientGameContext gameContext;
    @Autowired
    private WordPage wordPage;
    @Autowired
    private ItemHandle itemHandle;

    public void showEmail(){
        Message message = MessageUtil.createMessage(ModuleKey.EMAIL_MODULE,EmailCmd.EMAIL_LIST_REQ,null);
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = EmailCmd.EMAIL_LIST_REQ)
    public void receiveEmailList(Message message){
        try {
            EmailProtocol.EmailListRes emailListRes = EmailProtocol.EmailListRes.parseFrom(message.getData());
            if(emailListRes.getCode()!=0){
                wordPage.print(emailListRes.getMsg());
                return;
            }
            for(EmailProtocol.EmailInfo emailInfo:emailListRes.getEmailList()){
                emailInfoMap.put(emailInfo.getId(),emailInfo);
            }
            wordPage.clean();
            wordPage.printEmailList(emailListRes.getEmailList());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String title,String content,long receiverId,int goods,int bagIndex){
        EmailProtocol.SendEmailReq.Builder builder = EmailProtocol.SendEmailReq.newBuilder();
        builder.setTitle(title);
        builder.setContent(content);
        builder.setReceiverId(receiverId);
        builder.setGolds(goods);
        ItemProtocol.ItemInfo itemInfo = itemHandle.playerBagMap.get(bagIndex);
        if(itemInfo!=null){
            EmailProtocol.EmailAttachment.Builder at = EmailProtocol.EmailAttachment.newBuilder();
            at.setItemId(itemInfo.getItemId());
            builder.addAttachment(at);
        }
        Message message = MessageUtil.createMessage(ModuleKey.EMAIL_MODULE,EmailCmd.SEND_EMAIL,builder
                .build().toByteArray());
        gameContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = EmailCmd.SEND_EMAIL)
    public void handleSendEmailRes(Message message){
        try {
            EmailProtocol.SendEmailRes sendEmailRes = EmailProtocol.SendEmailRes.parseFrom(message.getData());
            wordPage.print(sendEmailRes.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }
}
