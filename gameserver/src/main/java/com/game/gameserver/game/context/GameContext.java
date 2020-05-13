package com.game.gameserver.game.context;

import com.game.entity.GameRole;
import com.game.pojo.GameMap;
import com.game.entity.Scene;
import com.game.pojo.User;
import com.game.gameserver.game.mapper.GameMapMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 游戏上下文，用于存储游戏数据
 * @author: xuewenkang
 * @date: 2020/5/12 16:09
 */
@Getter
@Component
public class GameContext {

    private final static Logger logger = LoggerFactory.getLogger(GameContext.class);

    /**
     * channel attr key
     * */
    public final static AttributeKey<User> CHANNEL_USER_KEY = AttributeKey.valueOf("USER");

    /**
     * 用户组 存储已经登录的用户的Channel
     * */
    private ChannelGroup userGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 用户ID为Key ChannelID为值
     */
    private final ConcurrentHashMap<Integer, ChannelId> userChannelIdMap = new ConcurrentHashMap<>(16);

    /**
     * 游戏地图
     * key为地图编号，地图为值
     */
    private final HashMap<Integer, GameMap> maps = new HashMap<>(16);

    /**
     * 游戏场景
     * key为场景ID 该ID与地图ID相同，值为场景
     * */
    private final HashMap<Integer, Scene> scenes = new HashMap<>(16);

    /**
     * 用户角色
     * key为用户ID，值为用户角色
     * */
    private final ConcurrentHashMap<Integer, GameRole> roles = new ConcurrentHashMap<>(16);

    @Autowired
    private GameMapMapper gameMapMapper;

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
            gameMap.setWays(new ArrayList<>());
            String[] ids = gameMap.getWay().split(",");
            for(String id:ids){
                GameMap tmp = maps.get(Integer.parseInt(id));
                gameMap.getWays().add(tmp);
            }
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
        }
    }

    /**
     * 构建无向图
     * */
    private void adjustMaps(){

    }
}
