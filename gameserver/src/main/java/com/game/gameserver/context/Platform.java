package com.game.gameserver.context;

import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.auction.manager.AuctionHouseManager;
import com.game.gameserver.module.guild.dao.GuildDbService;
import com.game.gameserver.module.guild.dao.GuildMapper;
import com.game.gameserver.module.guild.manager.GuildManager;
import com.game.gameserver.module.instance.manager.InstanceManager;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.shop.manager.ShopManager;
import com.game.gameserver.thread.BusinessThreadPool;
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
    private BusinessThreadPool businessThreadPool;
    @Autowired
    private SceneManager sceneManager;
    @Autowired
    private AuctionHouseManager auctionHouseManager;
    @Autowired
    private ShopManager shopManager;
    @Autowired
    private GuildManager guildManager;
    @Autowired
    private InstanceManager instanceManager;

    public void startUp() {
        logger.info("platform start up ......");
        // 初始化业务线程池
        businessThreadPool.initialize();
        // 加载静态资源
        staticConfigManager.loadConfig();
        // 加载场景
        sceneManager.loadScene();
        // 加载副本
        instanceManager.initialize();
        // 加载拍卖行
        auctionHouseManager.loadAuctionItem();
        // 加载商店
        shopManager.loadShop();
        // 加载公会
        guildManager.loadGuild();
    }

}
