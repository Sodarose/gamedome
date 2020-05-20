package com.game.game.context;

import com.game.game.service.GameService;
import com.game.game.service.AccountService;
import com.game.task.MessageDispatcher;
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
public class ClientSpringContext implements ApplicationContextAware {
    private Channel channel;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GameService gameService;

    public static ApplicationContext application;

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
