package com.game.gameserver.service.impl;

import com.game.config.MessageType;
import com.game.entity.GameRole;
import com.game.entity.Monster;
import com.game.entity.Npc;
import com.game.entity.Scene;
import com.game.gameserver.annotation.CmdHandler;
import com.game.gameserver.context.GameContext;
import com.game.gameserver.handler.MessageDispatcher;
import com.game.gameserver.manager.RoleManager;
import com.game.gameserver.manager.SceneManager;
import com.game.gameserver.mapper.RoleMapper;
import com.game.gameserver.service.AbstractGameService;
import com.game.pojo.GameMap;
import com.game.pojo.User;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.game.ulit.MessageUtil;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 */
@Service
public class GameServiceImpl extends AbstractGameService {

    private final static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private SceneManager sceneManager;

    @CmdHandler(cmd = MessageType.GAME_SCENE_S)
    @Override
    public void handleScene(Message message, Channel channel) {
        logger.info("handle game scene req");
        Attribute<User> attr = channel.attr(GameContext.CHANNEL_USER_KEY);
        GameRole gameRole = roleManager.getGameRole(attr.get().getId());
        Scene scene  = sceneManager.getScene(gameRole.getMapId());
        if(scene==null){
            logger.info("角色未加载完毕");
            return;
        }
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
        Message resMsg = MessageUtil.createMessage(MessageType.GAME_SCENE_S,sceneBuilder.build().toByteArray());
        channel.writeAndFlush(resMsg);
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
