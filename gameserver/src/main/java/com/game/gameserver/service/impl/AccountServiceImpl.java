package com.game.gameserver.service.impl;

import com.game.config.MessageType;
import com.game.gameserver.annotation.CmdHandler;
import com.game.gameserver.service.AccountService;
import com.game.protocol.Message;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.color.CMMException;

/**
 * @author xuewenkang
 */
@Service
public class AccountServiceImpl implements AccountService {

    @PostConstruct
    public void init(){

    }

    @CmdHandler(cmd = MessageType.USER_LOGIN_REQ)
    @Override
    public void login(Message message) {

    }

}
