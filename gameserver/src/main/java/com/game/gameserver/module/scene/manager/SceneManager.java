package com.game.gameserver.module.scene.manager;

import com.game.gameserver.common.config.*;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.instance.event.EntryInstanceEvent;
import com.game.gameserver.module.instance.event.ExitInstanceEvent;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.model.SceneObject;
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

    public SceneManager() {
        instance = this;
    }

    @Autowired
    private PlayerManager playerManager;

    /**
     * 已经创建的场景
     */
    private Map<Long, SceneObject> sceneObjectMap = new ConcurrentHashMap<>(4);

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
            SceneObject sceneObject = new SceneObject(sceneConfig.getValue(), sceneMonsterConfig, sceneNpcConfig);
            // 场景初始化
            sceneObject.initialize();
            sceneObjectMap.put(sceneObject.getId(), sceneObject);
        }
    }

    public SceneObject getSceneObject(Long sceneId) {
        return sceneObjectMap.get(sceneId);
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
        for (Map.Entry<Long, SceneObject> entry : sceneObjectMap.entrySet()) {
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
        SceneObject sceneObject = sceneObjectMap.get(sceneId);
        if (sceneObject == null) {
            sceneObject = sceneObjectMap.get(1001);
        }
        boolean result = sceneObject.entry(playerObject);
        if (!result) {
            return;
        }
        // 同步场景数据 (暂时直接同步场景数据)
        SceneProtocol.SceneInfo sceneInfo = ProtocolFactory.createSceneInfo(sceneObject);
        Message message = MessageUtil.createMessage(ModuleKey.SCENE_MODULE, SceneCmd.SYNC_SCENE
                , sceneInfo.toByteArray());
        // 广播
        sceneObject.broadcast(message);
    }


    /**
     * 退出场景
     *
     * @param playerObject
     * @return boolean
     */
    public void exitScene(PlayerObject playerObject) {
        SceneObject sceneObject = sceneObjectMap.get(playerObject.getPlayer().getSceneId());
        if (sceneObject == null) {
            return;
        }
        boolean result = sceneObject.exit(playerObject);
        if (!result) {
            return;
        }
        // 同步场景数据 (暂时直接同步场景数据)
        SceneProtocol.SceneInfo sceneInfo = ProtocolFactory.createSceneInfo(sceneObject);
        Message message = MessageUtil.createMessage(ModuleKey.SCENE_MODULE, SceneCmd.SYNC_SCENE
                , sceneInfo.toByteArray());
        // 广播
        sceneObject.broadcast(message);
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

    /**
     * 同步场景所有数据
     *
     * @param sceneId
     * @return void
     */
    public void syncSceneAllInfo(Long sceneId) {

    }

    /**
     * 同步场景怪物数据
     *
     * @param sceneId
     * @param monsterId
     * @return void
     */
    public void syncSceneMonsterInfo(Long sceneId, Long... monsterId) {

    }

    /**
     * 同步场景npc数据
     *
     * @param sceneId
     * @param npcId
     * @return void
     */
    public void syncSceneNpcInfo(Long sceneId, Long... npcId) {

    }

    /**
     * 同步场景内玩家数据
     *
     * @param sceneId
     * @param playerId
     * @return void
     */
    public void syncScenePlayerInfo(Long sceneId, Long... playerId) {

    }


}
