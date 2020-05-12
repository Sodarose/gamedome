package com.game.service.impl;

import com.game.annotation.CmdHandler;
import com.game.config.MessageType;
import com.game.context.ClientContext;
import com.game.context.GameContext;
import com.game.handler.MessageDispatcher;
import com.game.page.PageManager;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.game.service.AbstractAccountService;
import com.game.service.AbstractGameService;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author xuewenkang
 */
@Service
public class AccountServiceImpl extends AbstractAccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private ClientContext clientContext;

    @Autowired
    private PageManager pageManager;

    @Autowired
    private AbstractGameService gameService;

    @Override
    public void login(String loginId, String password) {
        logger.info("user loginId {} do login ......",loginId);
        Protocol.LoginReq loginReq = Protocol.LoginReq.newBuilder()
                .setLoginId(loginId)
                .setPassword(password)
                .build();
        Message message = createMessage(MessageType.USER_LOGIN_REQ,loginReq.toByteArray());
        clientContext.getChannel().writeAndFlush(message);
    }

    @Override
    public void register(String loginId, String password) {
        logger.info("user loginId {} do register ......",loginId);
        Protocol.RegisterReq registerReq = Protocol.RegisterReq.newBuilder()
                .setLoginId(loginId)
                .setPassword(password)
                .build();
        Message message = createMessage(MessageType.USER_REGISTER_REQ,registerReq.toByteArray());
        clientContext.getChannel().writeAndFlush(message);
    }

    @CmdHandler(cmd = MessageType.USER_LOGIN_RES)
    @Override
    public void handleLoginRes(Message message) {
        logger.info("handle login response message {}",message);
        try {
            Protocol.LoginRes loginRes = Protocol.LoginRes.parseFrom(message.getData());
            if(loginRes.getCode()!=0){
                pageManager.showMessageDialog(loginRes.getMsg());
                return;
            }
            pageManager.showMessageDialog("登录成功 即将跳转");
            gameService.iniGameClient();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            logger.error("message{} parse error ",message);
        }
    }

    @CmdHandler(cmd = MessageType.USER_REGISTER_RES)
    @Override
    public void handleRegisterRes(Message message) {
        logger.info("handle register response message {}",message);
        try {
            Protocol.RegisterRes registerRes = Protocol.RegisterRes.parseFrom(message.getData());
            if(registerRes.getCode()!=0){
                pageManager.showMessageDialog(registerRes.getMsg());
                return;
            }
            pageManager.showMessageDialog("注册成功");
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            logger.error("message{} parse error ",message);
        }
    }

    @PostConstruct
    public void register(){
        logger.info("register service ......");
        messageDispatcher.registerService(this);
    }

    private Message createMessage(short cmd,byte[] bytes){
        int length = 6;
        if(bytes!=null){
            length+=bytes.length;
        }
        return  new Message(length,cmd,bytes);
    }
}
