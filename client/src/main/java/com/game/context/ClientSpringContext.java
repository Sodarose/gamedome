package com.game.context;


import com.game.module.order.handle.CmdHandle;
import com.game.module.player.handle.PlayerHandle;
import com.game.module.account.handle.AccountHandle;
import com.game.module.scene.handle.SceneHandle;
import com.game.module.scene.handle.TipHandle;
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
    private AccountHandle accountHandle;
    @Autowired
    private PlayerHandle playerHandle;
    @Autowired
    private CmdHandle cmdHandle;
    @Autowired
    private SceneHandle sceneHandle;
    @Autowired
    private TipHandle tipHandle;


    public static ApplicationContext application;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       application = applicationContext;
    }
}
