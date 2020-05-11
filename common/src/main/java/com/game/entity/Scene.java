package com.game.entity;


import lombok.Data;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * 游戏场景
 */
@Data
public class Scene {

    /**
     * 场景标识ID
     * */
    private Integer id;

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
    private ConcurrentHashMap<Integer,GameNpc> npc;

    /**
     * 场景内怪物列表
     * */
    private ConcurrentHashMap<Integer,Monster> monster;

    /**
     * 移动到其他场景的路径
     * */
    private List<Way> ways;
}
