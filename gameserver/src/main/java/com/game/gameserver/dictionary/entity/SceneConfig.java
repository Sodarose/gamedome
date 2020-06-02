package com.game.gameserver.dictionary.entity;

import lombok.Data;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/2 20:18
 */
@Data
public class SceneConfig {
    private int id;
    private List<NpcConfig> npcConfig;
    private List<MonsterConfigData> monsterConfig;
}
