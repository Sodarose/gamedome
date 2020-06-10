package com.game.gameserver.module.scene.manager;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.npc.manager.NpcManager;
import com.game.gameserver.module.npc.model.NpcObject;
import com.game.gameserver.module.scene.model.SceneObject;
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
public class SceneManager {

    private final static Logger logger  = LoggerFactory.getLogger(SceneManager.class);

    @Autowired
    private MonsterManager monsterManager;
    @Autowired
    private NpcManager npcManager;

    /** 已经创建的场景 */
    private Map<Integer, SceneObject> sceneObjectMap = new ConcurrentHashMap<>(4);

    /** 读取场景配置 创建场景 */
    public void loadScene(){
        Map<Integer, SceneConfig> sceneConfigMap = StaticConfigManager.getInstance().getSceneConfigMap();
        for(Map.Entry<Integer,SceneConfig> sceneConfig:sceneConfigMap.entrySet()){
            // 获取场景怪物配置
            SceneMonsterConfig sceneMonsterConfig = StaticConfigManager.getInstance()
                    .getSceneMonsterConfigMap().get(sceneConfig.getValue().getMonsterConfig());
            // 获取场景NPC配置
            SceneNpcConfig sceneNpcConfig = StaticConfigManager.getInstance()
                    .getSceneNpcConfigMap().get(sceneConfig.getValue().getNpcConfig());
            // 创建场景对象
            SceneObject sceneObject = new SceneObject(sceneConfig.getValue(),sceneMonsterConfig,sceneNpcConfig);
            // 加载怪物配置
            loadSceneMonsterConfig(sceneObject);
            // 加载场景Npc配置
            loadSceneNpcConfig(sceneObject);
            // 场景初始化
            sceneObject.initialize();
            sceneObjectMap.put(sceneObject.getId(),sceneObject);
        }
    }

    /**
     * 加载场景怪物配置
     *
     * @param sceneObject
     * @return void
     */
    private void loadSceneMonsterConfig(SceneObject sceneObject){
        SceneMonsterConfig sceneMonsterConfig = sceneObject.getSceneMonsterConfig();
        if(sceneMonsterConfig==null){
            logger.info("scene {} don't have SceneMonsterConfig ",sceneObject.getSceneConfig().getName());
            return;
        }
        for(SceneMonster sceneMonster:sceneMonsterConfig.getSceneMonsterList()){
            List<MonsterObject> monsterObjectList = monsterManager.createMonsterObjectList(sceneMonster.getMonsterId(),
                    sceneMonster.getCount());
            for(MonsterObject monsterObject:monsterObjectList){
                sceneObject.addMonsterObject(monsterObject);
            }
        }
    }

    /**
     * 加载场景npc配置
     *
     * @param sceneObject
     * @return void
     */
    private void loadSceneNpcConfig(SceneObject sceneObject){
        SceneNpcConfig sceneNpcConfig = sceneObject.getSceneNpcConfig();
        if(sceneNpcConfig==null){
            logger.info("scene {} don't have SceneNpcConfig ",sceneObject.getSceneConfig().getName());
            return;
        }
        for(SceneNpc sceneNpc:sceneNpcConfig.getSceneNpcList()){
            NpcObject npcObject = npcManager.createNpcObject(sceneNpc.getNpcId());
            if(npcObject==null){
                continue;
            }
            sceneObject.addNpcObject(npcObject);
        }
    }
}
