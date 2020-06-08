package com.game.gameserver.module.scene.manager;

import com.game.gameserver.module.scene.object.SceneObject;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 场景管理器
 *
 * @author xuewenkang
 * @date 2020/6/8 19:33
 */
@Component
public class SceneManager {
    /** 已经创建的场景 */
    private Map<Integer, SceneObject> sceneObjectMap = new ConcurrentHashMap<>(4);

    public void loadScene(){

    }
}
