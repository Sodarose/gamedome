package com.game.gameserver.context;

import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.module.scene.manager.SceneManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 管理平台 负责整个服务器资源的初始化
 * @author xuewenkang
 * @date 2020/5/19 15:40
 */
@Component
public class Platform {

    private final static Logger logger = LoggerFactory.getLogger(Platform.class);

    @Autowired
    private DictionaryManager dictionaryManager;
    @Autowired
    private SceneManager sceneManager;

    public void startUp(){
        logger.info("platform start up ......");
        dictionaryManager.loadConfig();
        sceneManager.loadScene();
    }


}
