package com.game.gameserver.module.scene.service;

import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.module.scene.entity.SceneEntity2;
import com.game.gameserver.module.scene.manager.SceneManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 */
@Component
public class SceneService {
    @Autowired
    private SceneManager sceneManager;

    /**
     * 进入场景
     * @param playerObject 角色
     * @param targetSceneId 目标场景
     * */
    public void entryScene(PlayerObject playerObject,Integer targetSceneId){
        SceneEntity2 targetSceneEntity2 = sceneManager.getScene(targetSceneId);
        if(targetSceneEntity2 ==null){
            return;
        }
        targetSceneEntity2.entry(playerObject);
    }

    /**
     * 退出场景
     * @param playerObject 角色对象
     * */
    public void exitScene(PlayerObject playerObject){
        SceneEntity2 sceneEntity2 = sceneManager.getScene(playerObject.getPlayerEntity().getSceneId());
        if(sceneEntity2 ==null){
            return;
        }
        sceneEntity2.exit(playerObject);
    }

    /**
     * 切换场景
     * @param playerObject 角色
     * @param targetSceneId 目标场景
     * */
    public void changeScene(PlayerObject playerObject,Integer targetSceneId){
        SceneEntity2 sourceSceneEntity2 = sceneManager.getScene(playerObject.getPlayerEntity().getSceneId());
        if(!sourceSceneEntity2.getWays().contains(targetSceneId)){
            return;
        }
        exitScene(playerObject);
        entryScene(playerObject,targetSceneId);
    }
}
