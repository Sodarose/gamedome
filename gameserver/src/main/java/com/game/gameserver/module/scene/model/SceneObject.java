package com.game.gameserver.module.scene.model;

import com.game.gameserver.common.config.*;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.npc.model.NpcObject;
import com.game.gameserver.module.player.model.PlayerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 场景对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:15
 */
public class SceneObject implements Unit {

    private final static Logger logger = LoggerFactory.getLogger(SceneObject.class);

    /** id */
    private int id;
    /** 场景静态信息 */
    private final SceneConfig sceneConfig;
    /** 场景怪物配置信息 */
    private final SceneMonsterConfig sceneMonsterConfig;
    /** 场景Npc配置信息 */
    private final SceneNpcConfig sceneNpcConfig;
    /** 场景内玩家Map */
    private final Map<Integer,PlayerObject> playerMap = new ConcurrentHashMap<>();
    /** 场景内怪物Map */
    private final Map<Integer,MonsterObject> monsterMap = new ConcurrentHashMap<>();
    /** 场景内Npc Map */
    private final Map<Integer, NpcObject> npcMap = new ConcurrentHashMap<>();

    public SceneObject(SceneConfig sceneConfig,SceneMonsterConfig sceneMonsterConfig,SceneNpcConfig sceneNpcConfig){
        this.id = sceneConfig.getId();
        this.sceneConfig = sceneConfig;
        this.sceneMonsterConfig = sceneMonsterConfig;
        this.sceneNpcConfig = sceneNpcConfig;
    }

    public void initialize(){
        logger.info("Scene {} initialize ",sceneConfig.getName());
    }

    @Override
    public void update() {

    }

    /**
     * 添加玩家对象
     *
     * @param playerObject
     * @return boolean
     */
    public void addPlayerObject(PlayerObject playerObject){
       playerMap.put(playerObject.getId(),playerObject);
    }

    /**
     * 移除玩家对象
     *
     * @param playerId
     * @return com.game.gameserver.module.player.model.PlayerObject
     */
    public PlayerObject removePlayerObject(int playerId){
        return playerMap.remove(playerId);
    }

    /**
     * 外部添加一个怪物对象
     *
     * @param monsterObject
     * @return boolean
     */
    public void addMonsterObject(MonsterObject monsterObject){
        monsterMap.put(monsterObject.getId(),monsterObject);
    }

    /**
     * 移除一个怪物对象
     *
     * @param monsterId
     * @return com.game.gameserver.module.monster.model.MonsterObject
     */
    public MonsterObject removeMonsterObject(int monsterId){
        return monsterMap.remove(monsterId);
    }

    /**
     * 增加一个NPC对象
     *
     * @param npcObject
     * @return boolean
     */
    public void addNpcObject(NpcObject npcObject){
        npcMap.put(npcObject.getId(),npcObject);
    }


    /**
     * 移除一个Npc对象
     *
     * @param npcId
     * @return com.game.gameserver.module.npc.model.NpcObject
     */
    public NpcObject removeNpcObject(int npcId){
        return npcMap.remove(npcId);
    }

    public int getId() {
        return id;
    }

    public SceneConfig getSceneConfig(){
        return sceneConfig;
    }

    public SceneMonsterConfig getSceneMonsterConfig(){
        return sceneMonsterConfig;
    }

    public SceneNpcConfig getSceneNpcConfig() {
        return sceneNpcConfig;
    }
}
