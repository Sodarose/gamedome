package com.game.game.context;

import com.game.task.MessageDispatcher;
import com.game.game.service.AbstractAccountService;
import com.game.game.service.AbstractGameService;
import io.netty.channel.Channel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xuwenkang
 */

@Component
public class ClientContext implements ApplicationContextAware {
    private Channel channel;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private AbstractAccountService accountService;

    @Autowired
    private GameContext gameContext;

    @Autowired
    private AbstractGameService gameService;


    private  static ApplicationContext application;

    public void setChannel(Channel channel){
        this.channel = channel;
    }

    public Channel getChannel(){
        return channel;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        application = applicationContext;
    }
}