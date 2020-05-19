package com.game.gameserver.game.context;

import com.game.gameserver.task.MessageDispatcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 服务器上下文 用于获取bean和applicationContext
 * @author: xuewenkang
 * @date: 2020/5/12 16:09
 */
@Component
public class ServerContext implements ApplicationContextAware {

    @Autowired
    private MessageDispatcher messageDispatcher;

    private static ApplicationContext application;

    public static ApplicationContext getApplication(){
        return application;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        application = applicationContext;
    }
}
