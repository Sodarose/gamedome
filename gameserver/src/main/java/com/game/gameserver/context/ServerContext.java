package com.game.gameserver.context;

import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.net.handler.MessageDispatcher;
import com.game.gameserver.net.modelhandler.fighter.FighterHandle;
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
    @Autowired
    private SceneManager sceneManager;
    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private FighterHandle fighterHandle;

    private static ApplicationContext application;

    public static ApplicationContext getApplication(){
        return application;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        application = applicationContext;
    }
}
