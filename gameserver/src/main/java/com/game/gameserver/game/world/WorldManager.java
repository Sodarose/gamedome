package com.game.gameserver.game.world;

import com.game.gameserver.game.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 场景管理器
 * @author xuewenkang
 * @date 2020/5/19 10:03
 */
@Component
public class WorldManager {

    private final static Logger logger = LoggerFactory.getLogger(WorldManager.class);
    private final  static Integer WORLD_LENGTH = 4;

    private Map<Integer,World> worlds = new HashMap<>();

    public void startUp(){
        logger.info("world manager start up ......");
        init();
    }

    private void init(){
        loadWorld();
    }

    /**
     * 加载场景 目前只测试
     */
    private void loadWorld(){
        for(int i=0;i<WORLD_LENGTH;i++){
            World world = new World();
            world.setId(0);
            world.setName("场景"+i);
            world.setDescription("场景介绍");
            world.init();
            worlds.put(world.getId(),world);
        }
    }

    public void addPlayerToWorld(Player player){
        World world = worlds.get(player.getId());
        if(world==null){
            logger.warn("player {} enter a world {} that doesn't exist",player.getId(),player.getWorldId());
            return;
        }
        world.playerEntryWorld(player);
    }
}
