package com.game.gameserver.module.scene.entity;

import com.game.gameserver.dictionary.entity.SceneConfig;
import com.game.gameserver.dictionary.entity.SceneData;
import javafx.scene.Scene;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/6/2 21:47
 */
@Data
public class SceneEntity {
    private SceneData sceneData;
    private SceneConfig sceneConfig;

    public SceneEntity(){}

    public SceneEntity(SceneData sceneData,SceneConfig sceneConfig){
        this.sceneData = sceneData;
        this.sceneConfig = sceneConfig;
    }
}
