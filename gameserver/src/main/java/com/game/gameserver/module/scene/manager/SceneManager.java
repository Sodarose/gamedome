package com.game.gameserver.module.scene.manager;

import com.game.gameserver.common.config.*;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.instance.event.EntryInstanceEvent;
import com.game.gameserver.module.instance.event.ExitInstanceEvent;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.bean.Scene;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.scene.SceneCmd;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.Message;
import com.game.protocol.SceneProtocol;
import com.game.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 场景管理器
 *
 * @author xuewenkang
 * @date 2020/6/8 19:33
 */
@Component
@Listener
public class SceneManager {

    private final static Logger logger = LoggerFactory.getLogger(SceneManager.class);

    public static SceneManager instance;

    @Autowired
    private PlayerManager playerManager;

    /** 本地缓存的场景数据 */
    private final Map<Long, Scene> LOCAL_SCENE_MAP = new ConcurrentHashMap<>(4);

    public SceneManager() {
        instance = this;
    }

    /**
     * 读取场景配置 创建场景
     */
    public void loadScene() {
        Map<Long, SceneConfig> sceneConfigMap = StaticConfigManager.getInstance().getSceneConfigMap();
        for (Map.Entry<Long, SceneConfig> sceneConfig : sceneConfigMap.entrySet()) {
            // 获取场景怪物配置
            SceneMonsterConfig sceneMonsterConfig = StaticConfigManager.getInstance()
                    .getSceneMonsterConfigMap().get(sceneConfig.getValue().getSceneMonsterConfigId());
            // 获取场景NPC配置
            SceneNpcConfig sceneNpcConfig = StaticConfigManager.getInstance()
                    .getSceneNpcConfigMap().get(sceneConfig.getValue().getSceneNpcConfigId());
            // 创建场景对象
            Scene scene = new Scene(sceneConfig.getValue(), sceneMonsterConfig, sceneNpcConfig);
            // 场景初始化
            scene.initialize();
            LOCAL_SCENE_MAP.put(scene.getId(), scene);
        }
    }

    public Scene getScene(Long sceneId) {
        return LOCAL_SCENE_MAP.get(sceneId);
    }

    /**
     * 进入场景
     *
     * @param playerObject
     * @param sceneName
     * @return void
     */
    public void changeScene(PlayerObject playerObject, String sceneName) {
        Long sceneId = null;
        for (Map.Entry<Long, Scene> entry : LOCAL_SCENE_MAP.entrySet()) {
            SceneConfig sceneConfig = entry.getValue().getSceneConfig();
            if (sceneName.equals(sceneConfig.getName())) {
                sceneId = entry.getKey();
                break;
            }
        }
        if (sceneId == null) {
            return;
        }
        /** 退出原场景 */
        exitScene(playerObject);
        /** 进入新场景 */
        entryScene(playerObject, sceneId);
    }

    /**
     * 进入场景
     *
     * @param playerObject
     * @return boolean
     */
    public void entryScene(PlayerObject playerObject, Long sceneId) {
        Scene scene = LOCAL_SCENE_MAP.get(sceneId);
        if (scene == null) {
            scene = LOCAL_SCENE_MAP.get(1001);
        }
        boolean result = scene.entry(playerObject);
        if (!result) {
            return;
        }
    }


    /**
     * 退出场景
     *
     * @param playerObject
     * @return boolean
     */
    public void exitScene(PlayerObject playerObject) {
        Scene scene = LOCAL_SCENE_MAP.get(playerObject.getPlayer().getSceneId());
        if (scene == null) {
            return;
        }
        boolean result = scene.exit(playerObject);
        if (!result) {
            return;
        }
    }


    public void receiveInstancePlayers(List<Long> players) {
        if (players.isEmpty()) {
            return;
        }
        for (Long playerId : players) {
            PlayerObject playerObject = playerManager.getPlayerObject(playerId);
            if (playerObject == null) {
                continue;
            }
            entryScene(playerObject, playerObject.getPlayer().getSceneId());
        }
    }

    /**
     * 处理进入副本事件
     *
     * @param event
     * @return void
     */
    @EventHandler(type = EventType.ENTRY_INSTANCE)
    public void handleEntryInstanceEvent(EntryInstanceEvent event) {
        // 得到进入副本的名单
        List<Long> playerIds = event.getPlayerIds();
        // 让名单中的角色退出场景
        for (Long playerId : playerIds) {
            PlayerObject playerObject = playerManager.getPlayerObject(playerId);
            if(playerObject==null){
                continue;
            }
            exitScene(playerObject);
        }
    }

    @EventHandler(type = EventType.EXIT_INSTANCE)
    public void handleExitInstanceEvent(ExitInstanceEvent event){
        // 得到退出副本的名单
        List<Long> playerIds = event.getPlayerIds();
        // 让名单中的角色进入场景
        for (Long playerId : playerIds) {
            PlayerObject playerObject = playerManager.getPlayerObject(playerId);
            if(playerObject==null){
                continue;
            }
            entryScene(playerObject,playerObject.getPlayer().getSceneId());
        }
    }
}
