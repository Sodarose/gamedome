package com.game.service.impl;

import com.game.config.MessageType;
import com.game.context.ClientContext;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.game.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private ClientContext context;

    @Override
    public void login(String loginId, String password) {
        logger.info("user {} running login ",loginId);
        Protocol.loginReq loginReq = Protocol.loginReq.newBuilder()
                .setLoginId(loginId).setPassword(password).build();
        byte[] bytes = loginReq.toByteArray();
        Message loginMsg = new Message(6+bytes.length, MessageType.USER_LOGIN_REQ,bytes);
        context.getChannel().writeAndFlush(loginMsg);
    }

    @Override
    public void logout() {

    }
}
