package com.game.gameserver.game.service.impl;

import com.game.config.MessageType;
import com.game.entity.GameRole;
import com.game.entity.Monster;
import com.game.entity.Npc;
import com.game.entity.Scene;
import com.game.gameserver.game.mapper.RoleMapper;
import com.game.gameserver.task.annotation.CmdHandler;
import com.game.gameserver.game.context.GameContext;
import com.game.gameserver.task.MessageDispatcher;
import com.game.gameserver.game.service.AbstractGameService;
import com.game.pojo.GameMap;
import com.game.pojo.User;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.game.ulit.MessageUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

/**
 * 游戏服务 游戏逻辑处理
 * @author xuewenkang
 */
@Service
public class GameServiceImpl extends AbstractGameService {

    private final static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private GameContext gameContext;

    @Autowired
    private RoleMapper roleMapper;

    @CmdHandler(cmd = MessageType.GAME_SCENE_S)
    @Override
    public void handleScene(Message message, Channel channel)  {
        logger.info("handle game scene req");
        Attribute<User> attr = channel.attr(GameContext.CHANNEL_USER_KEY);
        GameRole gameRole = gameContext.getRoles().get(attr.get().getId());
        Scene scene = gameContext.getScenes().get(gameRole.getMapId());
        if(scene==null){
            logger.info("该场景未加载 ......");
            return;
        }
        Message resMsg = MessageUtil.createMessage(MessageType.GAME_SCENE_S,sceneToProtocolScene(scene).toByteArray());
        channel.writeAndFlush(resMsg);
    }

    @CmdHandler(cmd = MessageType.GAME_ROLE_S)
    @Override
    public void handleRole(Message message, Channel channel) {
        logger.info("handle game scene req");
        Attribute<User> attr = channel.attr(GameContext.CHANNEL_USER_KEY);
        GameRole gameRole = gameContext.getRoles().get(attr.get().getId());
        if(gameRole==null){
            logger.info("该用户角色未加载......");
        }
        Protocol.Role role = gameRoleToProtocolRole(gameRole);
        channel.writeAndFlush(MessageUtil.createMessage(MessageType.GAME_ROLE_S,role.toByteArray()));
    }

    @CmdHandler(cmd = MessageType.GAME_MOVE_S)
    @Override
    public void handleRoleMove(Message message, Channel channel) {
        logger.info("处理角色移动......");
        try {
            Attribute<User> attr = channel.attr(GameContext.CHANNEL_USER_KEY);
            GameRole gameRole = gameContext.getRoles().get(attr.get().getId());
            Protocol.MoveScene moveScene = Protocol.MoveScene.parseFrom(message.getData());
            Scene scene = gameContext.getScenes().get(gameRole.getMapId());
            Scene targetScene = gameContext.getScenes().get(moveScene.getSceneId());
            if(targetScene==null){
                logger.info("目标场景不存在");
                return;
            }
            // 从原场景中移除该用户
            scene.getRoles().remove(gameRole.getId());
            // 更新角色地图信息
            gameRole.setMapId(targetScene.getGameMap().getId());
            roleMapper.saveRoleByRole(gameRole);
            // 移动到目标场景中
            targetScene.getRoles().put(gameRole.getId(),gameRole);
            handleRole(null,channel);

            Message resMsg = MessageUtil.createMessage(MessageType.GAME_MOVE_S,
                    sceneToProtocolScene(targetScene).toByteArray());
            channel.writeAndFlush(resMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            logger.error("RoleMove parse error");
        }
    }

    @CmdHandler(cmd = MessageType.GAME_CUT_S)
    @Override
    public void handleCutMap(Message message, Channel channel) {
        logger.info("处理地图切换......");
        try {
            Protocol.Scene scene = Protocol.Scene.parseFrom(message.getData());
            Scene targetScene = null;
            String name = scene.getMap().getName();
            for(Map.Entry<Integer,Scene> entry:gameContext.getScenes().entrySet()){
                if(entry.getValue().getGameMap().getName().equals(name)){
                    targetScene = entry.getValue();
                    break;
                }
            }
            if(targetScene==null){
                return;
            }
            Protocol.Scene sc = sceneToProtocolScene(targetScene);
            channel.writeAndFlush(MessageUtil.createMessage(MessageType.GAME_CUT_S,sc.toByteArray()));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            logger.error("CutScene parse error");
        }
    }




    private Protocol.Scene sceneToProtocolScene(Scene scene){
        Protocol.Scene.Builder sceneBuilder = Protocol.Scene.newBuilder();
        sceneBuilder.setMap(gameMapToProtocolMap(scene.getGameMap()));
        for(Map.Entry<Integer,GameRole> entry:scene.getRoles().entrySet()){
            sceneBuilder.putRoles(entry.getKey(),gameRoleToProtocolRole(entry.getValue()));
        }
        for(Map.Entry<Integer, Monster> entry:scene.getMonsters().entrySet()){
            sceneBuilder.putMonster(entry.getKey(),gameMonsterToProtocolMonster(entry.getValue()));
        }
        for(Map.Entry<Integer,Npc> entry:scene.getNpcs().entrySet()){
            sceneBuilder.putNpc(entry.getKey(),gameNpcToProtocolNpc(entry.getValue()));
        }
        return sceneBuilder.build();
    }

    private Protocol.Npc gameNpcToProtocolNpc(Npc npc){
        Protocol.Npc.Builder builder = Protocol.Npc.newBuilder();
        builder.setId(npc.getId());
        builder.setName(npc.getName());
        builder.setPh(npc.getPh());
        builder.setMp(npc.getMp());
        builder.setPhyAttack(npc.getPhyAttack());
        builder.setPhyDefense(npc.getPhyDefense());
        builder.setMagicAttack(npc.getMagicAttack());
        builder.setMagicDefense(npc.getMagicDefense());
        builder.setMapId(npc.getMapId());
        builder.setStatus(npc.getStatus());
        return builder.build();
    }

    private Protocol.Monster gameMonsterToProtocolMonster(Monster monster){
        Protocol.Monster.Builder builder = Protocol.Monster.newBuilder();
        builder.setId(monster.getId());
        builder.setName(monster.getName());
        builder.setPh(monster.getPh());
        builder.setMp(monster.getMp());
        builder.setPhyAttack(monster.getPhyAttack());
        builder.setPhyDefense(monster.getPhyDefense());
        builder.setMagicAttack(monster.getMagicAttack());
        builder.setMagicDefense(monster.getMagicDefense());
        builder.setMapId(monster.getMapId());
        builder.setStatus(monster.getStatus());
        return builder.build();
    }

    private Protocol.Role gameRoleToProtocolRole(GameRole gameRole){
        Protocol.Role.Builder builder = Protocol.Role.newBuilder();
        builder.setId(gameRole.getId());
        builder.setName(gameRole.getName());
        builder.setPh(gameRole.getPh());
        builder.setMp(gameRole.getMp());
        builder.setPhyAttack(gameRole.getPhyAttack());
        builder.setPhyDefense(gameRole.getPhyDefense());
        builder.setMagicAttack(gameRole.getMagicAttack());
        builder.setMagicDefense(gameRole.getMagicDefense());
        builder.setMapId(gameRole.getMapId());
        builder.setStatus(gameRole.getStatus());
        return builder.build();
    }

    private Protocol.Map gameMapToProtocolMap(GameMap gameMap){
        Protocol.Map.Builder mapBuilder = Protocol.Map.newBuilder();
        mapBuilder.setId(gameMap.getId());
        mapBuilder.setName(gameMap.getName());
        mapBuilder.setDescription(gameMap.getDescription());
        if(gameMap.getWays()!=null&&gameMap.getWays().size()!=0){
            for(GameMap map:gameMap.getWays()){
                Protocol.Map tmp = Protocol.Map.newBuilder()
                        .setId(map.getId())
                        .setName(map.getName())
                        .setDescription(map.getDescription()).build();
                mapBuilder.addWays(tmp);
            }
        }
        return mapBuilder.build();
    }

    @PostConstruct
    public void init(){
        messageDispatcher.registerService(this);
    }
}
