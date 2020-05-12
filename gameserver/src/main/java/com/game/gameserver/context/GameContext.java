package com.game.gameserver.context;

import com.game.gameserver.manager.SceneManager;
import com.game.pojo.GameMap;
import com.game.entity.Scene;
import com.game.pojo.User;
import com.game.gameserver.mapper.GameMapMapper;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 16:09
 *
 */
@Component
public class GameContext {

    private final static Logger logger = LoggerFactory.getLogger(GameContext.class);

    public final static AttributeKey<User> CHANNEL_USER_KEY = AttributeKey.valueOf("USER");
    /**
     * 用户组
     * */
    private ChannelGroup userGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 游戏地图
     */
    private final HashMap<Integer, GameMap> maps = new HashMap<>(16);

    /**
     * 游戏场景
     * */
    private final HashMap<Integer, Scene> scenes = new HashMap<>(16);

    @Autowired
    private GameMapMapper gameMapMapper;

    @Autowired
    private SceneManager sceneManager;

    /**
     * 读取资源
     */
    public void loadResource(){
        logger.info("load resource ......");
        loadMap();
        createScene();
        logger.info("load end ......");
    }

    /**
     * 读取地图资源
     * */
    private void loadMap(){
        Map<Integer, GameMap> maps = gameMapMapper.list();
        // 调整地图关系网
        for(Map.Entry<Integer, GameMap> entry:maps.entrySet()){
            GameMap gameMap = entry.getValue();
            this.maps.put(gameMap.getId(), gameMap);
        }
    }

    /**
     * 创建场景
     * */
    private void createScene(){
        for(java.util.Map.Entry<Integer, GameMap> entry:maps.entrySet()){
            GameMap gameMap = entry.getValue();
            Scene scene = new Scene(gameMap);
            scene.init();
            scenes.put(scene.getId(),scene);
            sceneManager.addScene(scene);
        }
    }

    /**
     * 构建无向图
     * */
    private void adjustMaps(){

    }

    public void addUserGroup(Channel channel){
        userGroup.add(channel);
    }

    public Scene getScene(Integer id){
        return scenes.get(id);
    }
}
