package com.game.gameserver.module.scene.manager;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.scene.model.SceneObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 场景管理器
 *
 * @author xuewenkang
 * @date 2020/6/8 19:33
 */
@Component
public class SceneManager {

    private final static Logger logger = LoggerFactory.getLogger(SceneManager.class);

    public static SceneManager instance;

    public SceneManager() {
        instance = this;
    }

    /**
     * 已经创建的场景
     */
    private Map<Integer, SceneObject> sceneObjectMap = new ConcurrentHashMap<>(4);

    /**
     * 读取场景配置 创建场景
     */
    public void loadScene() {
        Map<Integer, SceneConfig> sceneConfigMap = StaticConfigManager.getInstance().getSceneConfigMap();
        for (Map.Entry<Integer, SceneConfig> sceneConfig : sceneConfigMap.entrySet()) {
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

    public SceneObject getSceneObject(Integer sceneId) {
        return sceneObjectMap.get(sceneId);
    }

    /**
     * 进入场景
     *
     * @param playerObject
     * @param sceneName
     * @return void
     */
    public void entryScene(PlayerObject playerObject, String sceneName) {
        Integer sceneId = null;
        for (Map.Entry<Integer, SceneObject> entry : sceneObjectMap.entrySet()) {
            SceneConfig sceneConfig = entry.getValue().getSceneConfig();
            if (sceneName.equals(sceneConfig.getName())) {
                sceneId = entry.getKey();
                break;
            }
        }
        if (sceneId == null) {
            return;
        }
        entryScene(playerObject, sceneId);
    }

    /**
     * 进入场景
     *
     * @param playerObject
     * @return boolean
     */
    public void entryScene(PlayerObject playerObject, Integer sceneId) {
        SceneObject sceneObject = sceneObjectMap.get(sceneId);
        if (sceneObject == null) {
            sceneObject = sceneObjectMap.get(1001);
        }
        boolean result = sceneObject.entry(playerObject);
        if (!result) {
            return;
        }
        // 同步场景数据

        // 广播进入场景事件

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
        // 广播退出事件

    }


    /**
     * 场景更新
     *
     * @param
     * @return void
     */
    public void update() {
        //logger.info("更新场景状态 驱动AI");
        for (Map.Entry<Integer, SceneObject> entry : sceneObjectMap.entrySet()) {
            entry.getValue().update();
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
