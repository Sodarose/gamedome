package com.game.gameserver.module.scene.facade.impl;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.scene.entity.SceneEntity;
import com.game.gameserver.module.scene.facade.SceneFacade;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.scene.vo.SceneVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/5/24 18:08
 */
@Service
public class SceneFacadeImpl implements SceneFacade {
    private final static Logger logger = LoggerFactory.getLogger(SceneFacadeImpl.class);

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private SceneManager sceneManager;

    @Override
    public SceneVo entrySceneById(Integer playerId, Integer sceneId) {
        SceneEntity targetScene = sceneManager.getSceneEntity(sceneId);
        if(targetScene==null){
            logger.error("场景{}不存在",sceneId);
            return null;
        }
        Player player = playerManager.getPlayer(playerId);
        if(player ==null){
            logger.error("不存在该角色{}",playerId);
            return null;
        }
        SceneEntity originalScene = sceneManager.getSceneEntity(player.getSceneId());
        if(originalScene==null){
            logger.error("角色目前所在场景{}不存在",sceneId);
            return null;
        }
        SceneVo sceneVo = new SceneVo();
        if(player.getSceneId().equals(sceneId)){
            if(targetScene.existsPlayer(player.getId())){

            }else{
                targetScene.entryScene(player);
                return sceneVo;
            }
        }
        // 退出原场景
        originalScene.existsPlayer(playerId);
        // 进入新场景
        targetScene.entryScene(player);
        return sceneVo;
    }
}
