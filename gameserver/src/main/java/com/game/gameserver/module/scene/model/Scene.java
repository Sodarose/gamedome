package com.game.gameserver.module.scene.model;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.npc.model.Npc;

import com.game.gameserver.module.player.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 场景对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:15
 */
public class Scene {
    private final static Logger logger = LoggerFactory.getLogger(Scene.class);

    /** 场景Id */
    private final int sceneId;

    /** 场景名称 */
    private final String name;

    /** 场景简介 */
    private final String desc;

    /** 相邻场景 */
    private List<Integer> neighbors;

    /** 玩家表 */
    private Map<Long, Player> playerMap = new ConcurrentHashMap<>();

    /** 怪物表 */
    private Map<Long, Monster> monsterMap =  new ConcurrentHashMap<>();

    /** npc表 */
    private Map<Long,Npc> npcMap = new ConcurrentHashMap<>();

    public Scene(SceneConfig sceneConfig){
        this.sceneId = sceneConfig.getId();
        this.name = sceneConfig.getName();
        this.desc = sceneConfig.getDesc();
        this.neighbors = sceneConfig.getNeighbors();
    }

    public int getSceneId() {
        return sceneId;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public List<Integer> getNeighbors() {
        return neighbors;
    }

    public Map<Long, Monster> getMonsterMap() {
        return monsterMap;
    }

    public Map<Long, Npc> getNpcMap() {
        return npcMap;
    }

    public Map<Long, Player> getPlayerMap() {
        return playerMap;
    }
}
