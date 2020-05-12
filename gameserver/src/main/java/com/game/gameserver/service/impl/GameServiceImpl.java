package com.game.gameserver.service.impl;

import com.game.gameserver.handler.MessageDispatcher;
import com.game.gameserver.service.AbstractGameService;
import com.game.protocol.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author xuewenkang
 */
@Service
public class GameServiceImpl extends AbstractGameService {

    @Autowired
    private MessageDispatcher messageDispatcher;

    @PostConstruct
    public void init(){
        messageDispatcher.registerService(this);
    }

    @Override
    public void handleInitClient(Message message, Channel channel) {

    }
}
