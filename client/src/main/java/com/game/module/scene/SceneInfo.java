package com.game.module.scene;

import com.game.module.monster.Monster;
import com.game.module.npc.Npc;
import com.game.module.player.OtherPlayerInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kangkang
 */
@Data
public class SceneInfo {
    private long id;
    private String name;
    private String description;
    private int playerCount;
    private Map<Long, OtherPlayerInfo> playerMap = new HashMap<>();
    private Map<Long, Monster> monsterMap = new HashMap<>();
    private Map<Long, Npc> npcMap = new HashMap<>();
    private List<String> sceneExitWays = new ArrayList<>();
}
