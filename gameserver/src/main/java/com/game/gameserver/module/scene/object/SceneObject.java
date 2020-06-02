package com.game.gameserver.module.scene.object;

import com.game.gameserver.common.DefaultThreadFactory;
import com.game.gameserver.context.ServerContext;
import com.game.gameserver.dictionary.entity.MonsterConfigData;
import com.game.gameserver.dictionary.entity.NpcConfig;
import com.game.gameserver.dictionary.entity.SceneConfig;
import com.game.gameserver.module.monster.manager.MonsterManger;
import com.game.gameserver.module.monster.object.MonsterObject;
import com.game.gameserver.module.npc.manager.NpcManager;
import com.game.gameserver.module.npc.objcet.NpcObject;
import com.game.gameserver.module.player.object.NewPlayerObject;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.module.scene.entity.SceneEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xuewenkang
 * @date 2020/6/2 20:12
 */
public class SceneObject {
    private final static Logger logger = LoggerFactory.getLogger(SceneObject.class);

    /**
     * 场景唯一Id
     */
    private final int id;
    /**
     * 场景静态数据
     */
    private SceneEntity sceneEntity;

    /**
     * 玩家列表
     */
    private Map<Integer, NewPlayerObject> playerMap = new ConcurrentHashMap<>(1);
    /**
     * NPC列表
     */
    private Map<Integer, NpcObject> npcMap = new ConcurrentHashMap<>(1);
    /**
     * 怪物列表
     */
    private Map<Integer, MonsterObject> monsterMap = new ConcurrentHashMap<>(1);

    public SceneObject(int id, SceneEntity sceneEntity) {
        this.id = id;
        this.sceneEntity = sceneEntity;
    }

    public void initialize() {
        loadSceneConfig();
    }

    private void loadSceneConfig() {
        MonsterManger monsterManger = ServerContext.getApplication().getBean(MonsterManger.class);
        NpcManager npcManager = ServerContext.getApplication().getBean(NpcManager.class);
        logger.info("load SceneConfig ...............................................");
        List<MonsterConfigData> monsterConfigData = sceneEntity.getSceneConfig().getMonsterConfig();
        for (MonsterConfigData configData : monsterConfigData) {
            List<MonsterObject> monsterObjects = monsterManger.createMonsterObjectList(configData.getMonsterId(),
                    configData.getCount());
            if (monsterObjects == null) {
                continue;
            }
            monsterObjects.forEach(monsterObject -> {
                monsterMap.put(monsterObject.getId(), monsterObject);
            });
        }

        List<NpcConfig> npcConfigList = sceneEntity.getSceneConfig().getNpcConfig();
        for (NpcConfig npcConfig : npcConfigList) {
            List<NpcObject> npcObjects = npcManager.createNpcObjectList(npcConfig.getNpcId(),npcConfig.getCount());
            if(npcObjects==null){
                continue;
            }
            npcObjects.forEach(npcObject -> {
                npcMap.put(npcObject.getId(),npcObject);
            });
        }
    }


    /**
     * 玩家登录场景
     *
     * @param playerObject
     * @return void
     */
    public void login(PlayerObject playerObject) {

    }

    /**
     * 玩家进入场景
     *
     * @param playerObject
     * @return void
     */
    public void entry(PlayerObject playerObject) {

    }

    /**
     * 玩家退出场景
     *
     * @param playerObject
     * @return void
     */
    public void exit(PlayerObject playerObject) {

    }



}
