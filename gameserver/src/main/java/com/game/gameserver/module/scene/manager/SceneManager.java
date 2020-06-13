package com.game.gameserver.module.scene.manager;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.npc.manager.NpcManager;
import com.game.gameserver.module.scene.model.SceneObject;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final static Logger logger  = LoggerFactory.getLogger(SceneManager.class);

    public static SceneManager  instance;

    public SceneManager(){
        instance = this;
    }

    /** 已经创建的场景 */
    private Map<Integer, SceneObject> sceneObjectMap = new ConcurrentHashMap<>(4);

    /** 读取场景配置 创建场景 */
    public void loadScene(){
        Map<Integer, SceneConfig> sceneConfigMap = StaticConfigManager.getInstance().getSceneConfigMap();
        for(Map.Entry<Integer,SceneConfig> sceneConfig:sceneConfigMap.entrySet()){
            // 获取场景怪物配置
            SceneMonsterConfig sceneMonsterConfig = StaticConfigManager.getInstance()
                    .getSceneMonsterConfigMap().get(sceneConfig.getValue().getSceneMonsterConfigId());
            // 获取场景NPC配置
            SceneNpcConfig sceneNpcConfig = StaticConfigManager.getInstance()
                    .getSceneNpcConfigMap().get(sceneConfig.getValue().getSceneNpcConfigId());
            // 创建场景对象
            SceneObject sceneObject = new SceneObject(sceneConfig.getValue(),sceneMonsterConfig,sceneNpcConfig);
            // 场景初始化
            sceneObject.initialize();
            sceneObjectMap.put(sceneObject.getId(),sceneObject);
        }
    }

    public SceneObject getSceneObject(int sceneId){
        return sceneObjectMap.get(sceneId);
    }

    public void update(){
        //logger.info("更新场景状态 驱动AI");
        for(Map.Entry<Integer,SceneObject> entry:sceneObjectMap.entrySet()){
                entry.getValue().update();
        }
    }
}
