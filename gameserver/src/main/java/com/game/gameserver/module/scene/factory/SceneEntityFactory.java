package com.game.gameserver.module.scene.factory;

import com.game.gameserver.dictionary.dict.DictScene;
import com.game.gameserver.module.scene.entity.SceneEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 场景工厂
 * @author xuewenkang
 * @date 2020/5/26 11:07
 */
public class SceneEntityFactory {
    private final static Logger logger = LoggerFactory.getLogger(SceneEntity.class);

    public static SceneEntity createSceneEntity(DictScene dictScene){
        SceneEntity sceneEntity = new SceneEntity();
        sceneEntity.setDictScene(dictScene);
        return sceneEntity;
    }
}
