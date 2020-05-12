package com.game.gameserver.service.impl;

import com.game.config.MessageType;
import com.game.entity.GameRole;
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
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuewenkang
 */
@Service
public class GameServiceImpl extends AbstractGameService {

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private SceneManager sceneManager;

    @CmdHandler(cmd = MessageType.GAME_SCENE_S)
    @Override
    public void handleScene(Message message, Channel channel) {
        Attribute<User> attr = channel.attr(GameContext.CHANNEL_USER_KEY);
        GameRole gameRole = roleManager.getGameRole(attr.get().getId());
        Scene scene  = sceneManager.getScene(gameRole.getMapId());
        Protocol.Scene.Builder sceneBuilder = Protocol.Scene.newBuilder();
        sceneBuilder.setMap(gameMapToProtocolMap(scene.getGameMap()));

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
        return null;
    }

    private Protocol.Role gameRoleToProtocolRole(GameRole gameRole){
        return null;
    }

    private Protocol.Monster monsterToProtocolRole(){
        return null;
    }



    @PostConstruct
    public void init(){
        messageDispatcher.registerService(this);
    }
}
