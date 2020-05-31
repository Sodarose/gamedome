package com.game.gameserver.module.scene.entity;

import com.game.gameserver.dictionary.dict.DictScene;
import com.game.gameserver.module.monster.entity.Monster;
import com.game.gameserver.module.npc.entity.Npc;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.scene.SceneCmd;
import com.game.protocol.ActorProtocol;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.protocol.SceneProtocol;
import com.game.util.MessageUtil;
import org.omg.PortableInterceptor.DISCARDING;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 场景实体
 * @author xuewenkang
 * @date 2020/5/24 17:57
 */

public class Scene {
    private final static Logger logger = LoggerFactory.getLogger(Scene.class);

    public final static int ENTRY_SCENE = 0;
    public final static int EXIT_SCENE = 1;

    public final static String ENTRY_SCENE_MSG = "玩家[${0}]进入场景";
    public final static String EXIT_SCENE_MSG = "玩家[${0}]退出场景";

    private DictScene dictScene;
    /** 场景内怪物集合 */
    private final Map<Integer, Monster> monsterMap = new ConcurrentHashMap<>(1);
    /** 场景内NPC集合 */
    private final Map<Integer, Npc> npcMap = new ConcurrentHashMap<>(1);
    /** 玩家集合 */
    private final Map<Integer, PlayerObject> playerMap = new ConcurrentHashMap<>(1);
    /** 地图中玩家的数量 */
    private AtomicInteger playerCount = new AtomicInteger(0);
    /** 场景出口 */
    private List<Integer> ways = new ArrayList<>();


    /***
     * 根据DictScene 初始化场景
     */
    public void init(){
        logger.info("初始化 场景 {}",dictScene.toString());
        String[] waysString = dictScene.getWay().split("|");
        for(String way:waysString){
            ways.add(Integer.parseInt(way));
        }
    }

    public List<Integer> getWays(){
        return ways;
    }

    /**
     * 玩家进入场景
     * */
    public void entry(PlayerObject playerObject){
        playerObject.getPlayer().setSceneId(dictScene.getId());
        playerCount.getAndIncrement();

        // 发送提示
        SceneProtocol.SceneTip.Builder tipBuilder = SceneProtocol.SceneTip.newBuilder();
        tipBuilder.setCode(ENTRY_SCENE);
        tipBuilder.setMsg(ENTRY_SCENE_MSG.replace("${0}",playerObject.getPlayer().getName()));
        Message tipMsg = MessageUtil.createMessage(ModuleKey.TIP_MODEL, SceneCmd.SCENE_TIP,
                tipBuilder.build().toByteArray());
        playerObject.getChannel().writeAndFlush(tipMsg);

        // 同步场景数据给进入场景的玩家
        SceneProtocol.SyncSceneMessage syncSceneMessage = createSyncSceneMessage();
        Message syncMessage = MessageUtil.createMessage(ModuleKey.SCENE_MODEL,SceneCmd.SYNC_SCENE,
                syncSceneMessage.toByteArray());
        playerObject.getChannel().writeAndFlush(syncMessage);

        // 同步消息给场景中的其他玩家
        notifyAllPlayer(tipMsg);
        notifyAllPlayer(syncMessage);
        playerMap.put(playerObject.getPlayer().getId(),playerObject);
    }

    /**
     * 玩家退出场景
     * */
    public void exit(PlayerObject playerObject){

    }

    public void update(){

    }

    /**
     * 构建当前场景的同步数据
     * */
    public SceneProtocol.SyncSceneMessage createSyncSceneMessage(){
        SceneProtocol.SyncSceneMessage.Builder sceneBuilder = SceneProtocol.SyncSceneMessage.newBuilder();
        sceneBuilder.setId(dictScene.getId());
        sceneBuilder.setPlayerCount(playerCount.intValue());
        for(Map.Entry<Integer,PlayerObject> entry:playerMap.entrySet()){
            PlayerProtocol.SimplePlayerInfo.Builder simplePlayerBuilder =  PlayerProtocol.
                    SimplePlayerInfo.newBuilder();
            simplePlayerBuilder.setId(entry.getValue().getPlayer().getId());
            simplePlayerBuilder.setLevel(entry.getValue().getPlayer().getLevel());
            simplePlayerBuilder.setName(entry.getValue().getPlayer().getName());
            simplePlayerBuilder.setCareer(entry.getValue().getPlayer().getCareer());
            sceneBuilder.putPlayers(entry.getKey(),simplePlayerBuilder.build());
        }

        for(Map.Entry<Integer,Monster> entry:monsterMap.entrySet()){
            ActorProtocol.SimpleMonsterInfo.Builder builder = ActorProtocol.SimpleMonsterInfo.newBuilder();
            builder.setId(entry.getValue().getId());
            builder.setLevel(entry.getValue().getLevel());
            builder.setName(entry.getValue().getName());
            sceneBuilder.putMonsters(entry.getKey(),builder.build());
        }

        for(Map.Entry<Integer,Npc> entry:npcMap.entrySet()){
            ActorProtocol.SimpleNpcInfo.Builder builder = ActorProtocol.SimpleNpcInfo.newBuilder();
            builder.setId(entry.getValue().getId());
            builder.setName(entry.getValue().getName());
            builder.setCareer(entry.getValue().getCareer());
            builder.setLevel(entry.getValue().getLevel());
            sceneBuilder.putNpcs(entry.getKey(),builder.build());
        }
        return sceneBuilder.build();
    }

    public SceneProtocol.SyncEntryScene createSyncEntryScene(PlayerObject playerObject){
        PlayerProtocol.SimplePlayerInfo.Builder builder =  PlayerProtocol.
                SimplePlayerInfo.newBuilder();
        builder.setId(playerObject.getPlayer().getId());
        builder.setName(playerObject.getPlayer().getName());
        builder.setLevel(playerObject.getPlayer().getLevel());
        builder.setCareer(playerObject.getPlayer().getCareer());

        SceneProtocol.SyncEntryScene.Builder entryBuilder = SceneProtocol.SyncEntryScene.newBuilder();
        entryBuilder.setPlayer(builder.build());
        return entryBuilder.build();
    }

    public void notifyAllPlayer(Message message){
        for(Map.Entry<Integer,PlayerObject> entry:playerMap.entrySet()){
            entry.getValue().getChannel().writeAndFlush(message);
        }
    }

    public void setDictScene(DictScene dictScene){
        this.dictScene = dictScene;
    }

    public DictScene getDictScene(){
        return dictScene;
    }
}
