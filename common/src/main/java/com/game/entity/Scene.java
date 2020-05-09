package com.game.gameserver.entity;


import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * 游戏场景
 */
public class Scene {

    /**
     * 游戏地图
     * */
    private GameMap map;

    /**
     * 场景内玩家列表
     * */
    private ConcurrentHashMap<Integer, Role> user;

    /**
     * 场景NPC列表
     * */
    private ConcurrentHashMap<Integer,GameNpc> npc;

    /**
     * 场景内怪物列表
     * */
    private ConcurrentHashMap<Integer,Monster> monster;

}
