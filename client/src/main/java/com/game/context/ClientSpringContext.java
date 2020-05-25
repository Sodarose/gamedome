package com.game.context;

import com.game.module.game.service.GameService;
import com.game.module.account.service.AccountService;
import com.game.task.MessageDispatcher;
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

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GameService gameService;

    public static ApplicationContext application;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       application = applicationContext;
    }
}
