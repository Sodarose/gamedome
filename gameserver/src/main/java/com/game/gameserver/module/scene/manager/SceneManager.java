package com.game.gameserver.module.scene.manager;

import com.game.gameserver.dictionary.DictionaryManager;
import com.game.gameserver.dictionary.dict.DictScene;
import com.game.gameserver.module.scene.entity.SceneEntity;
import com.game.gameserver.module.scene.factory.SceneEntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/24 18:09
 */
@Component
public class SceneManager {
    private static final Logger logger = LoggerFactory.getLogger(SceneManager.class);

    private Map<Integer, SceneEntity> sceneEntityMap = new HashMap<>(4);

    @Autowired
    private DictionaryManager dictionaryManager;

    public void loadScene(){
        logger.info("加载场景");
        List<DictScene> dictScenes = dictionaryManager.getDictSceneList();
        for(DictScene dictScene:dictScenes){
            SceneEntity sceneEntity = SceneEntityFactory.createSceneEntity(dictScene);
            sceneEntityMap.put(sceneEntity.getDictScene().getId(),sceneEntity);
            sceneEntity.init();
        }
    }

    public SceneEntity getSceneEntity(Integer sceneId){
        return sceneEntityMap.get(sceneId);
    }

}
