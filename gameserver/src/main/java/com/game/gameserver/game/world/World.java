package com.game.gameserver.game.world;

import com.game.gameserver.game.monster.Monster;
import com.game.gameserver.game.npc.Npc;
import com.game.gameserver.game.player.Player;
import com.game.protocol.Message;
import com.game.protocol.MessageType;
import com.game.protocol.Protocol;
import com.game.util.MessageUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 场景
 * @author xuewenkang
 * @date 2020/5/19 10:03
 */
@Data
public class World {
    /**
     * 场景ID
     */
    private Integer id;

    /**
     * 场景名称
     * */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 场景中的玩家
     */
    private Map<Integer, Player> players;

    /**
     * 场景中的NPC
     */
    private Map<Integer, Npc> npcs;

    /**
     * 场景中的怪物
     */
    private Map<Integer, Monster> monsters;

    private volatile boolean stop = false;

    public void init(){
        players = new HashMap<>(16);
        npcs = new HashMap<>(16);
        monsters = new HashMap<>(16);
        loadNpc();
        loadMonster();
    }

    /**
     * 读取NPC信息
     * TIP：目前随机生成
     */
    private void loadNpc(){
        Random random = new Random();
        int length = random.nextInt(10);
        for(int i=0;i<length;i++){
            Npc npc = new Npc(i,"NPC"+i+"号",id);
            npcs.put(npc.getId(),npc);
        }
    }

    /**
     * 读取怪物信息
     * TIP: 目前随机生成
     */
    private void loadMonster(){
        Random random = new Random();
        int length = random.nextInt(10);
        for(int i=0;i<length;i++){
            Monster monster = new Monster(i,"NPC"+i+"号",id);
            monsters.put(monster.getId(),monster);
        }
    }

    public void playerEntryWorld(Player player){
        players.put(player.getId(),player);
        player.setWorldId(id);
    }

    public void playerExitWorld(Player player){
        players.remove(player.getWorldId());
    }

}
