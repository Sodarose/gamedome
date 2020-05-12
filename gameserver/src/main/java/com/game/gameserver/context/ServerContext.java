package com.game.gameserver.context;

import com.game.gameserver.handler.MessageDispatcher;
import com.game.gameserver.service.AbstractAccountService;
import com.game.gameserver.service.AbstractDbService;
import com.game.gameserver.service.AbstractGameService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 */

@Component
public class ServerContext implements ApplicationContextAware {

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private AbstractAccountService accountService;

    @Autowired
    private AbstractDbService dbService;

    @Autowired
    private AbstractGameService gameService;

    private static ApplicationContext application;


    public static ApplicationContext getApplication(){
        return application;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        application = applicationContext;
    }
}
