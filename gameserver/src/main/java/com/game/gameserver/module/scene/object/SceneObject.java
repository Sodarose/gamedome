package com.game.gameserver.module.scene.object;

import com.game.gameserver.common.config.SceneConfig;
import com.game.gameserver.common.config.SceneMonsterConfig;
import com.game.gameserver.common.config.SceneNpcConfig;
import com.game.gameserver.common.entity.Unit;
import com.game.gameserver.module.player.object.PlayerObject;

import java.util.Map;

/**
 * 场景对象
 *
 * @author xuewenkang
 * @date 2020/6/8 16:15
 */
public class SceneObject implements Unit {

    /** 唯一ID */
    private int id;
    /** 场景静态信息 */
    private SceneConfig sceneConfig;
    /** 场景怪物配置信息 */
    private SceneMonsterConfig sceneMonsterConfig;
    /** 场景Npc配置信息 */
    private SceneNpcConfig sceneNpcConfig;
    /** 场景内玩家Map */
    private Map<Integer,Integer> playerMap;
    /** 场景内怪物Map */
    private Map<Integer,Integer> monsterMap;
    /** 场景内Npc Map */
    private Map<Integer,Integer> npcMap;
    /** 场景内 副本入口Map */
    private Map<Integer,Integer> instanceMap;
    /** 场景内 出口Map */
    private Map<Integer,Integer> exitWayMap;

    @Override
    public void update() {

    }

    /**
     * 玩家进入场景
     *
     * @param playerObject
     * @return void
     */
    public void onEntry(PlayerObject playerObject){

    }

    /**
     * 玩家退出场景
     *
     * @param playerObject
     * @return void
     */
    public void onExit(PlayerObject playerObject){

    }
}
