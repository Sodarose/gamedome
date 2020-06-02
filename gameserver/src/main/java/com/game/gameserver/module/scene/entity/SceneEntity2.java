package com.game.gameserver.module.scene.entity;

import com.game.gameserver.module.monster.object.MonsterObject;
import com.game.gameserver.module.npc.entity.NpcEntity;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.scene.SceneCmd;
import com.game.protocol.ActorProtocol;
import com.game.protocol.Message;
import com.game.protocol.PlayerProtocol;
import com.game.protocol.SceneProtocol;
import com.game.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 场景实体
 * @author xuewenkang
 * @date 2020/5/24 17:57
 */

public class SceneEntity2 {
    private final static Logger logger = LoggerFactory.getLogger(SceneEntity2.class);

    public final static int ENTRY_SCENE = 0;
    public final static int EXIT_SCENE = 1;

    public final static String ENTRY_SCENE_MSG = "地图提示:玩家[${0}]进入场景";
    public final static String EXIT_SCENE_MSG = "地图提示:玩家[${0}]退出场景";

    /** 场景内怪物集合 */
    private final Map<Integer, MonsterObject> monsterMap = new ConcurrentHashMap<>(1);
    /** 场景内NPC集合 */
    private final Map<Integer, NpcEntity> npcMap = new ConcurrentHashMap<>(1);
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


    }

    public List<Integer> getWays(){
        return ways;
    }

    /**
     * 玩家进入场景
     * */
    public void entry(PlayerObject playerObject){

        playerCount.getAndIncrement();
        playerMap.put(playerObject.getPlayerEntity().getId(),playerObject);
        // 发送提示
        SceneProtocol.SceneTip.Builder tipBuilder = SceneProtocol.SceneTip.newBuilder();
        tipBuilder.setCode(ENTRY_SCENE);
        tipBuilder.setMsg(ENTRY_SCENE_MSG.replace("${0}",playerObject.getPlayerEntity().getName()));
        Message tipMsg = MessageUtil.createMessage(ModuleKey.TIP_MODEL, SceneCmd.SCENE_TIP,
                tipBuilder.build().toByteArray());
        //playerObject.getChannel().writeAndFlush(tipMsg);
        // 同步场景数据给进入场景的玩家
        SceneProtocol.SyncSceneMessage syncSceneMessage = createSyncSceneMessage();
        Message syncMessage = MessageUtil.createMessage(ModuleKey.SCENE_MODEL,SceneCmd.SYNC_SCENE,
                syncSceneMessage.toByteArray());
        //playerObject.getChannel().writeAndFlush(syncMessage);
        // 同步消息给场景中的其他玩家
        notifyAllPlayer(tipMsg);
        notifyAllPlayer(syncMessage);
    }

    /**
     * 玩家退出场景
     * */
    public void exit(PlayerObject playerObject){
        playerCount.getAndDecrement();
        playerMap.remove(playerObject.getPlayerEntity().getId());
        SceneProtocol.SceneTip.Builder tipBuilder = SceneProtocol.SceneTip.newBuilder();
        tipBuilder.setCode(EXIT_SCENE);
        tipBuilder.setMsg(EXIT_SCENE_MSG.replace("${0}",playerObject.getPlayerEntity().getName()));
        Message tipMsg = MessageUtil.createMessage(ModuleKey.TIP_MODEL, SceneCmd.SCENE_TIP,
                tipBuilder.build().toByteArray());
        //playerObject.getChannel().writeAndFlush(tipMsg);
        // 同步场景数据给进入场景的玩家
        SceneProtocol.SyncSceneMessage syncSceneMessage = createSyncSceneMessage();
        Message syncMessage = MessageUtil.createMessage(ModuleKey.SCENE_MODEL,SceneCmd.SYNC_SCENE,
                syncSceneMessage.toByteArray());
        //playerObject.getChannel().writeAndFlush(syncMessage);
        // 同步消息给场景中的其他玩家
        notifyAllPlayer(tipMsg);
        notifyAllPlayer(syncMessage);
    }

    public void update(){

    }

    /**
     * 构建当前场景的同步数据
     * */
    public SceneProtocol.SyncSceneMessage createSyncSceneMessage(){
        SceneProtocol.SyncSceneMessage.Builder sceneBuilder = SceneProtocol.SyncSceneMessage.newBuilder();

        sceneBuilder.setPlayerCount(playerCount.intValue());
        for(Map.Entry<Integer,PlayerObject> entry:playerMap.entrySet()){
            PlayerProtocol.SimplePlayerInfo.Builder simplePlayerBuilder =  PlayerProtocol.
                    SimplePlayerInfo.newBuilder();
            simplePlayerBuilder.setId(entry.getValue().getPlayerEntity().getId());
            simplePlayerBuilder.setLevel(entry.getValue().getPlayerEntity().getLevel());
            simplePlayerBuilder.setName(entry.getValue().getPlayerEntity().getName());
            simplePlayerBuilder.setCareer(entry.getValue().getPlayerEntity().getCareer());
            sceneBuilder.putPlayers(entry.getKey(),simplePlayerBuilder.build());
        }

        for(Map.Entry<Integer, MonsterObject> entry:monsterMap.entrySet()){
            ActorProtocol.SimpleMonsterInfo.Builder builder = ActorProtocol.SimpleMonsterInfo.newBuilder();
            builder.setId(entry.getValue().getId());
            builder.setLevel(entry.getValue().getLevel());
            builder.setName(entry.getValue().getName());
            sceneBuilder.putMonsters(entry.getKey(),builder.build());
        }

        for(Map.Entry<Integer, NpcEntity> entry:npcMap.entrySet()){
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
        builder.setId(playerObject.getPlayerEntity().getId());
        builder.setName(playerObject.getPlayerEntity().getName());
        builder.setLevel(playerObject.getPlayerEntity().getLevel());
        builder.setCareer(playerObject.getPlayerEntity().getCareer());

        SceneProtocol.SyncEntryScene.Builder entryBuilder = SceneProtocol.SyncEntryScene.newBuilder();
        entryBuilder.setPlayer(builder.build());
        return entryBuilder.build();
    }

    public void notifyAllPlayer(Message message){
        for(Map.Entry<Integer,PlayerObject> entry:playerMap.entrySet()){
            entry.getValue().getChannel().writeAndFlush(message);
        }
    }

}
