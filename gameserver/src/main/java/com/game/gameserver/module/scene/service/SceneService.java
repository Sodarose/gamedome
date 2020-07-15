package com.game.gameserver.module.scene.service;

import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.scene.helper.SceneHelper;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.scene.model.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/17 17:13
 */
@Service
public class SceneService  {
    private final static int BEGIN_SCENE = 1001;

    @Autowired
    private SceneManager sceneManager;


    public Scene getScene(int sceneId){
        return sceneManager.getScene(sceneId);
    }

    /**
     * 展示场景信息
     *
     * @param player
     * @return void
     */
    public void showScene(Player player){
        Scene currScene = player.getCurrScene();
        if(currScene ==null){
            NotificationHelper.notifyPlayer(player,"请先进入场景");
            return;
        }
        NotificationHelper.notifyPlayer(player,SceneHelper.buildScene(currScene));
    }

    /**
     * 场景列表
     *
     * @param
     * @return void
     */
    public void sceneList(Player player){
        List<Scene> scenes = sceneManager.getSceneList();
        StringBuilder sb = new StringBuilder("场景列表:");
        scenes.forEach(
                scene -> {
                    sb.append(scene.getName()).append("(")
                            .append(scene.getSceneId()).append(")").append("\t");
                }
        );
        sb.append("\n");
        NotificationHelper.notifyPlayer(player,sb.toString());
    }

    /**
     *
     * @param player
     * @param sceneId
     * @return void
     */
    public void checkScene(Player player, int sceneId){
        Scene scene = sceneManager.getScene(sceneId);
        if (scene==null) {
            NotificationHelper.notifyPlayer(player,"该场景不存在");
            return;
        }
        NotificationHelper.notifyPlayer(player,SceneHelper.buildScene(scene));
    }

    /**
     * 初始化角色进入场景
     *
     * @param player
     * @return void
     */
    public void initPlayerEntryScene(Player player){
        Scene scene = sceneManager.getScene(player.getPlayerEntity().getSceneId());
        if(scene == null){
            scene = sceneManager.getScene(BEGIN_SCENE);
        }
        if(scene == null){
            return;
        }
        // 将玩家放入场景中
        scene.getPlayerMap().put(player.getPlayerEntity().getId(),player);
        player.setCurrScene(scene);
        player.getPlayerEntity().setSceneId(scene.getSceneId());
        // 广播进入场景通知
        NotificationHelper.notifyScene(scene, MessageFormat.format("玩家{0}进入场景",
                player.getPlayerEntity().getName()));
        // 同步信息
        NotificationHelper.syncScene(scene);
    }

    /**
     * AIO 命令
     *
     * @param player
     * @return void
     */
    public void aio(Player player){
        Scene currScene = player.getCurrScene();
        if(currScene==null){
            return;
        }
        NotificationHelper.notifyPlayer(player, SceneHelper.buildAio(currScene));
    }

    /**
     * 退出当前场景
     *
     * @param player
     * @return void
     */
    public void exitScene(Player player){
        // 等到玩家当前所在场景
        Scene currScene = player.getCurrScene();
        // 移除玩家
        currScene.getPlayerMap().remove(player.getPlayerEntity().getId());
        player.setCurrScene(null);
        // 广播通知
        NotificationHelper.notifyScene(currScene, MessageFormat.format("玩家{0}退出场景",
                player.getPlayerEntity().getName()));
        // 同步信息
        NotificationHelper.syncScene(currScene);
    }

    /**
     * 移动场景
     *
     * @param player
     * @param sceneId
     * @return void
     */
    public void moveScene(Player player, int sceneId){
        // 得到当前玩家所在的场景
        Scene currScene = player.getCurrScene();
        // 判断目标场景是否可达
        if(!currScene.getNeighbors().contains(sceneId)){
            NotificationHelper.notifyPlayer(player,"目标场景不可达");
            return;
        }
        Scene targetScene = sceneManager.getScene(sceneId);
        if(targetScene==null){
            NotificationHelper.notifyPlayer(player,"目标场景不存在");
            return;
        }
        // 退出当前场景
        currScene.getPlayerMap().remove(player.getPlayerEntity().getId());
        // 进入目标场景
        targetScene.getPlayerMap().put(player.getPlayerEntity().getId(),player);
        player.setCurrScene(targetScene);
        player.getPlayerEntity().setSceneId(targetScene.getSceneId());
        // 通知
        NotificationHelper.notifyScene(currScene,MessageFormat.format("玩家{0}退出场景",
                player.getPlayerEntity().getName()));
        NotificationHelper.notifyScene(targetScene,MessageFormat.format("玩家{0}进入场景",
                player.getPlayerEntity().getName()));
        // 同步数据
        NotificationHelper.syncScene(currScene);
        NotificationHelper.syncScene(targetScene);
    }
}
