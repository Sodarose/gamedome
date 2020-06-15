package com.game.gameserver.module.scene.model;

import com.game.gameserver.common.config.*;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.monster.model.MonsterObject;
import com.game.gameserver.module.npc.manager.NpcManager;
import com.game.gameserver.module.npc.model.NpcObject;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.util.GameUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 场景对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:15
 */
public class SceneObject implements Unit {

    private final static Logger logger = LoggerFactory.getLogger(SceneObject.class);

    /** id */
    private Long id;
    /** 场景静态信息 */
    private final SceneConfig sceneConfig;
    /** 场景怪物配置信息 */
    private final SceneMonsterConfig sceneMonsterConfig;
    /** 场景Npc配置信息 */
    private final SceneNpcConfig sceneNpcConfig;
    /** 玩家数量 */
    private final AtomicInteger playerNum = new AtomicInteger(0);
    /** 场景内玩家Map */
    private final Map<Long,PlayerObject> playerMap = new ConcurrentHashMap<>();
    /** 场景内怪物Map */
    private final Map<Long,MonsterObject> monsterMap = new ConcurrentHashMap<>();
    /** 场景内Npc Map */
    private final Map<Long, NpcObject> npcMap = new ConcurrentHashMap<>();
    /** 场景出口 */
    private final List<String> sceneExitWays = new ArrayList<>();

    public SceneObject(SceneConfig sceneConfig,SceneMonsterConfig sceneMonsterConfig,SceneNpcConfig sceneNpcConfig){
        this.id = GameUUID.getInstance().generate();
        this.sceneConfig = sceneConfig;
        this.sceneMonsterConfig = sceneMonsterConfig;
        this.sceneNpcConfig = sceneNpcConfig;
    }

    public void initialize(){
        logger.info("Scene {} initialize ",sceneConfig.getName());
        loadSceneMonsterConfig();
        loadSceneNpcConfig();
        // 加载场景出口
        for(Integer id:sceneConfig.getExitWays()){
            SceneConfig sceneConfig = StaticConfigManager.getInstance().getSceneConfigMap().get(id);
            if(sceneConfig==null){
                continue;
            }
            sceneExitWays.add(sceneConfig.getName());
        }
    }


    /**
     * 加载怪物
     *
     * @param
     * @return void
     */
    private void loadSceneMonsterConfig(){
        if(sceneMonsterConfig==null){
            logger.info("scene {} don't have SceneMonsterConfig ",sceneConfig.getName());
            return;
        }
        for(SceneMonster sceneMonster:sceneMonsterConfig.getSceneMonsterList()){
            List<MonsterObject> monsterObjectList = MonsterManager.getInstance()
                    .createMonsterObjectList(sceneMonster.getMonsterId(),
                    sceneMonster.getCount());
            for(MonsterObject monsterObject:monsterObjectList){
                monsterMap.put(monsterObject.getId(),monsterObject);
            }
        }
    }


    /**
     * 加载场景Npc
     *
     * @param
     * @return void
     */
    private void loadSceneNpcConfig(){
        if(sceneNpcConfig==null){
            logger.info("scene {} don't have SceneNpcConfig ",sceneConfig.getName());
            return;
        }
        for(SceneNpc sceneNpc:sceneNpcConfig.getSceneNpcList()){
            NpcObject npcObject = NpcManager.instance.createNpcObject(sceneNpc.getNpcId());
            if(npcObject==null){
                continue;
            }
            npcMap.put(npcObject.getId(),npcObject);
        }
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
       playerMap.put(playerObject.getUnitId(),playerObject);
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

    public long getId() {
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

    public List<String> getSceneExitWays(){
        return sceneExitWays;
    }

    public int getPlayerNum(){
        return playerNum.get();
    }

    public Map<Long, PlayerObject> getPlayerObjectMap(){
        return playerMap;
    }

    public Map<Long,MonsterObject> getMonsterObjectMap(){
        return monsterMap;
    }

    public Map<Long,NpcObject> getNpcObjectMap(){
        return npcMap;
    }
}
