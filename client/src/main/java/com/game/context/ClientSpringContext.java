package com.game.context;


import com.game.module.instance.InstanceHandle;
import com.game.module.order.CmdHandle;
import com.game.module.player.PlayerHandle;
import com.game.module.account.AccountHandle;
import com.game.module.scene.SceneHandle;
import com.game.module.tip.TipHandle;
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
    @Autowired
    private InstanceHandle instanceHandle;


    public static ApplicationContext application;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       application = applicationContext;
    }
}
