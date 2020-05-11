package com.game.gameserver.handler;

import com.game.entity.GameMap;
import com.game.entity.Scene;
import com.game.gameserver.mapper.GameMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * 场景管理
 */
@Component
public class SceneManager {

    @Autowired
    private GameMapMapper mapMapper;

    /**
     * 场景
     * */
    private final HashMap<Integer, Scene> sceneHashMap = new HashMap<>(16);

    /**
     * 初始化场景
     * */
    public void init(){
        Map<Integer,GameMap> maps = mapMapper.list();
        for(Map.Entry<Integer,GameMap> entry:maps.entrySet()){
            GameMap map = entry.getValue();
            map.setWays(new ArrayList<>());
            String[] ids = map.getWay().split(",");
            for(String id:ids){
                GameMap temp = maps.get(Integer.parseInt(id));
                if(temp!=null){
                    map.getWays().add(temp);
                }
            }

            Scene scene = new Scene();
            scene.init(map);
            sceneHashMap.put(scene.getId(),scene);
        }
    }

    /**
     * 构建无向图邻接表
     * */
    private void adjust(Map<Integer,GameMap> maps){

    }





}
