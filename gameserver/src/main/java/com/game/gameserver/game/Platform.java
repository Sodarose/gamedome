package com.game.gameserver.game;

import com.game.gameserver.game.world.WorldManager;
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
    private WorldManager worldManager;

    public void startUp(){
        logger.info("platform start up ......");
        worldManager.startUp();
    }


}
