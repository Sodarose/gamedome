package com.game.gameserver.module.scene.service;

import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.module.scene.entity.Scene;
import com.game.gameserver.module.scene.manager.SceneManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        Scene targetScene = sceneManager.getScene(targetSceneId);
        if(targetScene==null){
            return;
        }
        targetScene.entry(playerObject);
    }

    /**
     * 退出场景
     * @param playerObject 角色对象
     * */
    public void exitScene(PlayerObject playerObject){
        Scene scene = sceneManager.getScene(playerObject.getPlayer().getSceneId());
        if(scene==null){
            return;
        }
        scene.exit(playerObject);
    }

    /**
     * 切换场景
     * @param playerObject 角色
     * @param targetSceneId 目标场景
     * */
    public void changeScene(PlayerObject playerObject,Integer targetSceneId){
        Scene sourceScene = sceneManager.getScene(playerObject.getPlayer().getSceneId());
        if(!sourceScene.getWays().contains(targetSceneId)){
            return;
        }
        exitScene(playerObject);
        entryScene(playerObject,targetSceneId);
    }
}
