package com.game.gameserver.module.scene.manager;

import com.game.gameserver.dictionary.StaticDataManager;
import com.game.gameserver.dictionary.entity.SceneConfig;
import com.game.gameserver.dictionary.entity.SceneData;
import com.game.gameserver.module.scene.entity.SceneEntity;
import com.game.gameserver.module.scene.object.SceneObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuewenkang
 * @date 2020/5/24 18:09
 */
@Component
public class SceneManager {
    private final static  Logger logger = LoggerFactory.getLogger(SceneManager.class);
    private final static AtomicInteger SCENE_ID_GEN = new AtomicInteger(0);
    private Map<Integer, SceneObject> scenes = new HashMap<>(4);

    private StaticDataManager staticDataManager = StaticDataManager.getInstance();

    /**
     * 加载场景
     * */
    public void loadScene(){

    }

    private void createSceneObject(){
        Map<Integer,SceneData> sceneDict = staticDataManager.getSceneDict();
        Map<Integer, SceneConfig> sceneConfigDict = staticDataManager.getSceneConfigDict();
        List<SceneEntity> sceneEntities = new ArrayList<>();
        for(Map.Entry<Integer,SceneData> entry:sceneDict.entrySet()){
            SceneConfig sceneConfig = sceneConfigDict.get(entry.getValue().getSceneConfig());
            if(sceneConfig==null){
                logger.warn("Scene {} don't have SceneConfig ",entry.getValue().getName());
                continue;
            }
            SceneEntity sceneEntity = new SceneEntity(entry.getValue(),sceneConfig);
            sceneEntities.add(sceneEntity);
        }
        for(SceneEntity sceneEntity:sceneEntities){
            SceneObject sceneObject = new SceneObject(SCENE_ID_GEN.getAndIncrement(),sceneEntity);
            initScene(sceneObject);
        }
    }

    private void initScene(SceneObject sceneObject){
        sceneObject.initialize();
    }

    /**
     * 重置场景
     * @param sceneObject
     * @return void
     */
    public void restScene(SceneObject sceneObject){

    }

}
