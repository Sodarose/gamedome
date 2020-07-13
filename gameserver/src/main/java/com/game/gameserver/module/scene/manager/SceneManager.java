package com.game.gameserver.module.scene.manager;

import com.game.gameserver.common.config.*;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.player.event.LoginEvent;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.scene.model.Scene;
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

import java.util.ArrayList;
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

    public SceneManager() {
        instance = this;
    }

    @Autowired
    private PlayerManager playerManager;

    /**
     * 已经创建的场景
     */
    private Map<Long, Scene> sceneMap = new ConcurrentHashMap<>(4);

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
            sceneMap.put(scene.getId(), scene);
        }
    }

    public List<Scene> getSceneList() {
        List<Scene> scenes = new ArrayList<>();
        for (Map.Entry<Long, Scene> entry : sceneMap.entrySet()) {
            scenes.add(entry.getValue());
        }
        return scenes;
    }

    public Scene getScene(Long sceneId) {
        return sceneMap.get(sceneId);
    }

    /**
     * 进入场景
     *
     * @param player
     * @param sceneName
     * @return void
     */
    public void changeScene(Player player, String sceneName) {
        Long sceneId = null;
        for (Map.Entry<Long, Scene> entry : sceneMap.entrySet()) {
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
        exitScene(player);
        /** 进入新场景 */
        entryScene(player, sceneId);
    }

    /**
     * 进入场景
     *
     * @param player
     * @return boolean
     */
    public void entryScene(Player player, Long sceneId) {
        Scene scene = sceneMap.get(sceneId);
        if (scene == null) {
            scene = sceneMap.get(1001);
        }
        boolean result = scene.entry(player);
        if (!result) {
            return;
        }
        // 同步场景数据 (暂时直接同步场景数据)
        SceneProtocol.SceneInfo sceneInfo = ProtocolFactory.createSceneInfo(scene);
        Message message = MessageUtil.createMessage(ModuleKey.SCENE_MODULE, SceneCmd.SYNC_SCENE
                , sceneInfo.toByteArray());
        // 广播
        scene.broadcast(message);
    }


    /**
     * 退出场景
     *
     * @param player
     * @return boolean
     */
    public void exitScene(Player player) {
        Scene scene = sceneMap.get(player.getSceneId());
        if (scene == null) {
            return;
        }
        boolean result = scene.exit(player);
        if (!result) {
            return;
        }
        // 同步场景数据 (暂时直接同步场景数据)
        SceneProtocol.SceneInfo sceneInfo = ProtocolFactory.createSceneInfo(scene);
        Message message = MessageUtil.createMessage(ModuleKey.SCENE_MODULE, SceneCmd.SYNC_SCENE
                , sceneInfo.toByteArray());
        // 广播
        scene.broadcast(message);
    }


    public void receiveInstancePlayers(List<Long> players) {
        if (players.isEmpty()) {
            return;
        }
        for (Long playerId : players) {
            Player player = playerManager.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            entryScene(player, player.getSceneId());
        }
    }

    /**
     * 处理用户登录事件 将用户加载进入场景
     *
     * @param event
     * @return void
     */
    @EventHandler(type = EventType.EVENT_TYPE_LOGIN)
    public void handleLoginEvent(LoginEvent event){
        // 获取玩家角色
        long playerId = event.getPlayerId();
        Player player = playerManager.getPlayer(playerId);
        if(player==null){
            return;
        }
        // 让玩家进入场景
        entryScene(player,player.getSceneId());
    }
}
