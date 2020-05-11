package com.game.entity;


import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * 游戏场景
 */
@Data
public class Scene {

    private Integer id;

    private volatile boolean init = false;

    /**
     * 游戏地图
     * */
    private GameMap map;

    /**
     * 场景内玩家列表
     * */
    private ConcurrentHashMap<Integer, GameRole> user;

    /**
     * 场景NPC列表
     * */
    private HashMap<Integer,GameNpc> npc;

    /**
     * 场景内怪物列表
     * */
    private HashMap<Integer,Monster> monsters;

    /**
     * 创建怪物
     */
    private void createMonster(){
        monsters = new HashMap<>(16);
        Random rn = new Random();
        int length = rn.nextInt(10);
        for(int i=0;i<length;i++){
            Monster monster = new Monster();
            monster.setName("怪物"+i+"号");
            monster.setId(i);
            monster.setDescription("怪物"+i+"号");
            monster.setPh(10000);
            monster.setMp(10000);
            monsters.put(id,monster);
        }
    }

    /**
     * 创建Npc
     * */
    private void createNpc(){
        npc = new HashMap<>(16);
        Random rn = new Random();
        int length = rn.nextInt(5);
        for(int i=0;i<length;i++){
            GameNpc gameNpc = new GameNpc();
            gameNpc.setId(i);
            gameNpc.setName("NPC"+i+"号");
            gameNpc.setDescription("NPC"+i+"号");
            gameNpc.setPh(1000);
            gameNpc.setMp(1000);
            npc.put(gameNpc.getId(),gameNpc);
        }
    }

    /**
     * 加载地图
     * */
    private void loadMap(GameMap gameMap){
        id = gameMap.getId();
        map = gameMap;

    }

    /**
     * 场景初始化
     * */
    public void init(final GameMap map){
        loadMap(map);
        createNpc();
        createMonster();
    }
}
