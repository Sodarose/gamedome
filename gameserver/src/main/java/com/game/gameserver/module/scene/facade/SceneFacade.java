package com.game.gameserver.module.scene.facade;

import com.game.gameserver.module.scene.vo.SceneVo;

/**
 * 场景接口
 * @author xuewenkang
 * @date 2020/5/24 18:08
 */
public interface SceneFacade {
    /**
     * 进入指定的场景
     * @param playerId
     * @param sceneId
     * @return void
     */
    SceneVo entrySceneById(Integer playerId, Integer sceneId);
}
