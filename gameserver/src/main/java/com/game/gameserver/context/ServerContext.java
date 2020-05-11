package com.game.gameserver.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 */

@Component
public class ServerContext implements ApplicationContextAware {

    private  static ApplicationContext application;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        application = applicationContext;
    }

    public static ApplicationContext getApplication(){
        return application;
    }
}
