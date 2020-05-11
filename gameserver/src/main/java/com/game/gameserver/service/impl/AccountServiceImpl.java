package com.game.gameserver.service.impl;

import com.game.config.MessageType;
import com.game.entity.User;
import com.game.gameserver.annotation.CmdHandler;
import com.game.gameserver.handler.MessageDispatcher;
import com.game.gameserver.mapper.AccountMapper;
import com.game.gameserver.service.AccountService;
import com.game.gameserver.service.DbService;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.color.CMMException;
import java.util.UUID;

/**
 * @author xuewenkang
 */
@Service
public class AccountServiceImpl extends AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private AccountMapper accountMapper;

    @PostConstruct
    public void init(){
        messageDispatcher.registerService(this);
    }


    @CmdHandler(cmd = MessageType.USER_LOGIN_REQ)
    @Override
    public void login(Message message) {
        logger.info("user login by message {}",message);
        try {
            Protocol.loginReq loginReq =  Protocol.loginReq.parseFrom(message.getData());
            User user = accountMapper.findUserByLoginId(loginReq.getLoginId());
            if(user==null){
                return;
            }
            if(!user.getPassword().equals(loginReq.getPassword())){
                return;
            }
            String token = UUID.randomUUID().toString();
            Protocol.loginRes loginRes = Protocol.loginRes
                    .newBuilder()
                    .setId(user.getId())
                    .setToken(token)
                    .build();

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            logger.error("message parse error {}",message);
        }
    }
}
