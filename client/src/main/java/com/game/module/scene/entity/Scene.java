package com.game.module.scene.entity;

import com.game.module.monster.Monster;
import com.game.module.npc.Npc;
import com.game.module.player.model.Player;
import com.game.protocol.ActorProtocol;
import com.game.protocol.PlayerProtocol;
import com.game.protocol.SceneProtocol;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/5/26 16:24
 */
@Data
public class Scene {
    private Integer id;
    private String  name;
    private String  description;
    private Integer playerCount;
    Map<Integer, Player> playerMap;
    Map<Integer, Monster> monsterMap;
    Map<Integer, Npc> npcMap;

    public Scene(){}

    public Scene(SceneProtocol.SyncSceneMessage syncSceneMessage){
        this.id = syncSceneMessage.getId();
        this.name = syncSceneMessage.getName();
        this.description = syncSceneMessage.getDescription();
        this.playerCount = syncSceneMessage.getPlayerCount();
        playerMap = new HashMap<>();
        monsterMap = new HashMap<>();
        npcMap = new HashMap<>();
        for(Map.Entry<Integer, PlayerProtocol.SimplePlayerInfo> entry:syncSceneMessage.getPlayersMap().entrySet()){
            Player player = new Player(entry.getValue());
            playerMap.put(entry.getKey(),player);
        }

        for(Map.Entry<Integer, ActorProtocol.SimpleMonsterInfo> entry:syncSceneMessage.getMonstersMap().entrySet()){
            Monster monster = new Monster(entry.getValue());
            monsterMap.put(entry.getKey(),monster);
        }

        for(Map.Entry<Integer,ActorProtocol.SimpleNpcInfo> entry:syncSceneMessage.getNpcsMap().entrySet()){
            Npc npc = new Npc(entry.getValue());
            npcMap.put(entry.getKey(),npc);
        }
    }
}
