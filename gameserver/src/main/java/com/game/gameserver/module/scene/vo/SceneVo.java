package com.game.gameserver.module.scene.vo;

import lombok.Data;

import java.util.Map;

/**
 * 用来返回个客户端的场景数据
 * @author xuewenkang
 * @date 2020/5/24 20:03
 */
@Data
public class SceneVo {
    private Integer id;
    private String name;
    private Integer playerCount;
    private String description;
    /** 玩家列表  */
    private Map<Integer,String> players;
    /** 怪物列表 */
    private Map<Integer,String> monsters;
    /** npc列表 */
    private Map<Integer,String> npcs;
}
