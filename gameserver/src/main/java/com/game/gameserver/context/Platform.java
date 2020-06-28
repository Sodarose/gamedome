package com.game.gameserver.context;

import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.ai.manager.AiManager;
import com.game.gameserver.module.chat.manager.ChatManager;
import com.game.gameserver.module.cooltime.manager.CoolTimeManager;
import com.game.gameserver.module.instance.manager.InstanceManager;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.store.manager.StoreManager;
import com.game.gameserver.module.ai.thread.UnitTickThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
    @Autowired
    private ChatManager chatManager;
    @Autowired
    private StoreManager storeManager;
    @Autowired
    private InstanceManager instanceManager;
    @Autowired
    private AiManager aiManager;
    @Autowired
    private CoolTimeManager coolTimeManager;
    private UnitTickThread unitTickThread = new UnitTickThread(1, TimeUnit.SECONDS);

    public void startUp() {
        logger.info("platform start up ......");
        staticConfigManager.loadConfig();
        sceneManager.loadScene();
        chatManager.initialize();
        storeManager.loadStore();
        // 启动副本定时器
        instanceManager.startInstanceWorker();
        // AI定时器
        aiManager.startInstanceWorker();
        // CD 定时器
        coolTimeManager.startCoolTimeWorker();
        unitTickThread.start();
    }

}
