package com.game.gameserver.module.scene.entity;

import com.game.gameserver.dictionary.dict.DictScene;
import com.game.gameserver.module.monster.entity.MonsterEntity;
import com.game.gameserver.module.npc.entity.NpcEntity;
import com.game.gameserver.module.player.entity.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 场景实体
 * @author xuewenkang
 * @date 2020/5/24 17:57
 */
public class SceneEntity {
    private final static Logger logger = LoggerFactory.getLogger(SceneEntity.class);

    private Integer id;
    private String sceneName;
    private DictScene dictScene;
    /** 场景内怪物集合 */
    private Map<Integer, MonsterEntity> monsterMap = new ConcurrentHashMap<>(1);

    /** 场景内NPC集合 */
    private Map<Integer, NpcEntity> npcMap = new ConcurrentHashMap<>(1);

    /** 玩家集合 */
    private Map<Integer, PlayerEntity> playerMap = new ConcurrentHashMap<>(1);

    /** 地图中玩家的数量 */
    private AtomicInteger playerCount = new AtomicInteger(0);

    public SceneEntity(DictScene dictScene){
        this.dictScene = dictScene;
    }

    /***
     * 根据DictScene 初始化场景
     */
    public void init(){
        logger.info("初始化 场景 {}",dictScene.toString());
        id = dictScene.getId();
        sceneName = dictScene.getName();
    }

    /**
     * 进入场景
     * @param player 用户
     */
    public void entryScene(PlayerEntity player){

    }

    /**
     * 退出场景
     */
    public void exitScene(Integer id){

    }

    /**
     * 更新场景数据
     */
    public void update(){

    }


}
