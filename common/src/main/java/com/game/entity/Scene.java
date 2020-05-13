package com.game.entity;


import com.game.config.RoleStatus;
import com.game.pojo.GameMap;
import com.game.pojo.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * 游戏场景
 */
@Data
@NoArgsConstructor
public class Scene {

    private Integer id;

    /**
     * 游戏地图
     * */
    private GameMap gameMap;

    /**
     * 场景内玩家列表
     * */
    private ConcurrentHashMap<Integer, GameRole> roles;

    /**
     * 场景NPC列表
     * */
    private HashMap<Integer, Npc> npcs;

    /**
     * 场景内怪物列表
     * */
    private HashMap<Integer,Monster> monsters;

    public Scene(GameMap gameMap){
        this.gameMap = gameMap;
    }

    /**
     * 场景初始化
     * 初始化 怪物 NPC
     */
    public void init(){
        id = gameMap.getId();
        roles = new ConcurrentHashMap<>(16);
        npcs = new HashMap<>(16);
        monsters = new HashMap<>(16);
        createNpcs();
        createMonster();
    }

    /**
     * 场景生成NPC
     * */
    private void createNpcs(){
        Random random = new Random();
        int rn = random.nextInt(5)+1;
        for(int i=0;i<rn;i++){
            Npc npc = new Npc();
            npc.setId(i);
            npc.setPh(1000);
            npc.setMp(1000);
            npc.setName("NPC"+i+"号");
            npc.setPhyAttack(1000);
            npc.setPhyDefense(1000);
            npc.setMagicAttack(1000);
            npc.setMagicDefense(1000);
            npc.setMapId(id);
            npc.setStatus(RoleStatus.ROLE_LIVE);
            npcs.put(npc.getId(),npc);
        }
    }

    /**
     * 场景生成怪物
     * */
    private void createMonster(){
        Random random = new Random();
        int rn = random.nextInt(10)+1;
        for(int i=0;i<rn;i++){
            Monster monster = new Monster();
            monster.setId(i);
            monster.setPh(1000);
            monster.setMp(1000);
            monster.setName("怪物"+i+"号");
            monster.setPhyAttack(1000);
            monster.setPhyDefense(1000);
            monster.setMagicAttack(1000);
            monster.setMagicDefense(1000);
            monster.setMapId(id);
            monster.setStatus(RoleStatus.ROLE_LIVE);
            monsters.put(monster.getId(),monster);
        }
    }
}
