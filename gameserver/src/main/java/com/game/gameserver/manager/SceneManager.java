package com.game.gameserver.manager;

import com.game.entity.GameRole;
import com.game.entity.Scene;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 16:42
 */
@Component
public class SceneManager {

    private final HashMap<Integer,Scene> scenes = new HashMap<>();

    /**
     * 用户进入场景
     * */
    public void intoScene(GameRole gameRole){
        Scene scene = scenes.get(gameRole.getMapId());
        gameRole.setScene(scene);
    }

    public void addScene(Scene scene){
        scenes.put(scene.getId(),scene);
    }

    public Scene getScene(Integer id){
        return scenes.get(id);
    }
}
