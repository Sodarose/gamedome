package com.game.gameserver.module.scene.service;

import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.model.SceneObject;

/**
 * @author xuewenkang
 * @date 2020/6/10 20:33
 */
public interface SceneService {
    /**
     * 进入场景
     *
     * @param playerObject
     * @return boolean
     */
    boolean entryScene(PlayerObject playerObject);

    /**
     * 退出场景
     *
     * @param playerObject
     * @return boolean
     */
    boolean exitScene(PlayerObject playerObject);

    /**
     * 根据场景Id 获取场景对象
     *
     * @param sceneId
     * @return com.game.gameserver.module.scene.model.SceneObject
     */
    SceneObject getSceneObject(int sceneId);
}
