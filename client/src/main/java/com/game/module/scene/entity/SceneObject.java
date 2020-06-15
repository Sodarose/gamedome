package com.game.module.scene.entity;

import com.game.module.monster.Monster;
import com.game.module.npc.Npc;
import com.game.module.player.entity.OtherPlayerInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SceneObject {
    private long id;
    private String name;
    private String description;
    private int playerCount;
    private Map<Long, OtherPlayerInfo> playerMap;
    private Map<Long, Monster> monsterMap;
    private Map<Long, Npc> npcMap;
    private List<String> sceneExitWays;
}
