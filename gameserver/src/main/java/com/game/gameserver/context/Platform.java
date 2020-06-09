package com.game.gameserver.context;

import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.timewheel.manager.TimeWheelTimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 管理平台 负责整个服务器资源的初始化
 *
 * @author xuewenkang
 * @date 2020/5/19 15:40
 */
@Component
public class Platform {
    private final static Logger logger = LoggerFactory.getLogger(Platform.class);

    private StaticConfigManager staticConfigManager = StaticConfigManager.getInstance();

    @Autowired
    private SceneManager sceneManager;

    public void startUp() {
        logger.info("platform start up ......");
        staticConfigManager.loadConfig();
        sceneManager.loadScene();
    }

}
