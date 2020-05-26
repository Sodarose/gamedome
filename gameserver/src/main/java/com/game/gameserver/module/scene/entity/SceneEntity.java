package com.game.gameserver.module.scene.entity;

import com.game.gameserver.dictionary.dict.DictScene;
import com.game.gameserver.module.monster.entity.MonsterEntity;
import com.game.gameserver.module.npc.entity.NpcEntity;
import com.game.gameserver.module.player.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 场景实体
 * @author xuewenkang
 * @date 2020/5/24 17:57
 */
public class SceneEntity {
    private final static Logger logger = LoggerFactory.getLogger(SceneEntity.class);

    private DictScene dictScene;

    /** 场景内怪物集合 */
    private Map<Integer, MonsterEntity> monsterMap = new ConcurrentHashMap<>(1);

    /** 场景内NPC集合 */
    private Map<Integer, NpcEntity> npcMap = new ConcurrentHashMap<>(1);

    /** 玩家集合 */
    private Map<Integer, Player> playerMap = new ConcurrentHashMap<>(1);

    /** 地图中玩家的数量 */
    private AtomicInteger playerCount = new AtomicInteger(0);

    private Lock lock = new ReentrantLock();

    /***
     * 根据DictScene 初始化场景
     */
    public void init(){
        logger.info("初始化 场景 {}",dictScene.toString());
    }

    /**
     * 进入场景
     * @param player 用户
     */
    public void entryScene(Player player){
        playerMap.put(player.getId(),player);
        playerCount.getAndIncrement();
        // 广播进入事件
    }

    /**
     * 退出场景
     */
    public void exitScene(Integer id){
        Player player =  playerMap.remove(id);
        if(player ==null){
            return;
        }
        playerCount.getAndDecrement();
        // 广播退出事件
    }
    
    /***
     * 玩家是否已经在本场景
     */
    public boolean existsPlayer(Integer id){
        lock.lock();
        try{
            return playerMap.containsKey(id);
        }finally {
            lock.unlock();
        }

    }

    /**
     * 更新场景数据
     */
    public void update(){
        
    }

    public void setDictScene(DictScene dictScene){
        this.dictScene = dictScene;
    }

    public DictScene getDictScene(){
        return this.dictScene;
    }
}
