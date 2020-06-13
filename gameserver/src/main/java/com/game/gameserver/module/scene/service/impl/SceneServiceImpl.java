package com.game.gameserver.module.scene.service.impl;

import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.scene.model.SceneObject;
import com.game.gameserver.module.scene.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/6/10 21:18
 */
@Service
public class SceneServiceImpl implements SceneService {

    @Autowired
    private SceneManager sceneManager;

    /**
     * 进入场景
     *
     * @param playerObject
     * @return boolean
     */
    @Override
    public boolean entryScene(PlayerObject playerObject) {
        SceneObject sceneObject =  sceneManager
                .getSceneObject(playerObject.getPlayer().getSceneId());
        if(sceneObject==null){
            return false;
        }
        return false;
    }

    /**
     * 退出场景
     *
     * @param playerObject
     * @return boolean
     */
    @Override
    public boolean exitScene(PlayerObject playerObject) {
        return false;
    }

    /**
     * 根据场景Id 获取场景对象
     *
     * @param sceneId
     * @return com.game.gameserver.module.scene.model.SceneObject
     */
    @Override
    public SceneObject getSceneObject(int sceneId) {
        return null;
    }
}
