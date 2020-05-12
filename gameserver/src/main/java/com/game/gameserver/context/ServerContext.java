package com.game.gameserver.context;

import com.game.gameserver.handler.MessageDispatcher;
import com.game.gameserver.service.AccountService;
import com.game.gameserver.service.DbService;
import com.game.gameserver.service.GameService;
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
    private AccountService accountService;

    @Autowired
    private DbService dbService;

    @Autowired
    private GameService gameService;

    private  static ApplicationContext application;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        application = applicationContext;
    }

    public static ApplicationContext getApplication(){
        return application;
    }
}
