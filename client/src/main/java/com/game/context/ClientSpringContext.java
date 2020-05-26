package com.game.context;


import com.game.module.order.service.CmdService;
import com.game.module.player.service.PlayerService;
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
    private PlayerService playerService;
    @Autowired
    private CmdService cmdService;


    public static ApplicationContext application;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       application = applicationContext;
    }
}
