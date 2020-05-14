package com.game.gameserver.game.service.impl;

import com.game.config.MessageType;
import com.game.entity.GameRole;
import com.game.entity.Scene;
import com.game.gameserver.game.mapper.RoleMapper;
import com.game.gameserver.task.annotation.CmdHandler;
import com.game.gameserver.game.context.GameContext;
import com.game.gameserver.task.MessageDispatcher;
import com.game.gameserver.game.service.AbstractGameService;
import com.game.pojo.User;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.game.util.MessageUtil;
import com.game.util.ProtocolUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 游戏服务 游戏逻辑处理 客户端发送的指令处理
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
        logger.info("处理场景请求......");
        GameRole gameRole = getGameRoleByChannelAttr(channel);
        Scene scene = gameContext.getScenes().get(gameRole.getMapId());
        if(scene==null){
            logger.info("该场景未加载 ......");
            return;
        }
        Message resMsg = MessageUtil.createMessage(MessageType.GAME_SCENE_S,ProtocolUtil.sceneToProtocolScene(scene).toByteArray());
        channel.writeAndFlush(resMsg);
    }

    @CmdHandler(cmd = MessageType.GAME_ROLE_S)
    @Override
    public void handleRole(Message message, Channel channel) {
        logger.info("处理角色请求......");
        GameRole gameRole = getGameRoleByChannelAttr(channel);
        if(gameRole==null){
            logger.info("该用户角色未加载......");
            return;
        }
        Protocol.Role role = ProtocolUtil.gameRoleToProtocolRole(gameRole);
        channel.writeAndFlush(MessageUtil.createMessage(MessageType.GAME_ROLE_S,role.toByteArray()));
    }

    @CmdHandler(cmd = MessageType.GAME_MOVE_S)
    @Override
    public void handleRoleMove(Message message, Channel channel) {
        logger.info("处理角色移动......");
        try {
            GameRole gameRole = getGameRoleByChannelAttr(channel);
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
            // 移动到目标场景中
            targetScene.getRoles().put(gameRole.getId(),gameRole);
            handleSave(null,channel);
            Message resMsg = MessageUtil.createMessage(MessageType.GAME_MOVE_S,
                    ProtocolUtil.sceneToProtocolScene(targetScene).toByteArray());
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
            Protocol.Scene sc = ProtocolUtil.sceneToProtocolScene(targetScene);
            channel.writeAndFlush(MessageUtil.createMessage(MessageType.GAME_CUT_S,sc.toByteArray()));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            logger.error("CutScene parse error");
        }
    }

    @CmdHandler(cmd = MessageType.GAME_SAVE_S)
    @Override
    public void handleSave(Message message, Channel channel) {
        logger.info("执行用户角色保存");
        GameRole gameRole = getGameRoleByChannelAttr(channel);
        if(gameRole==null){
            logger.info("该用户角色不存在");
            return;
        }
        roleMapper.saveRoleByRole(gameRole);
    }

    /**
     * 根据Channel attr 获得当前Channel的用户的角色
     * @param channel 用户Channel
     * @return com.game.entity.GameRole
     */
    private GameRole getGameRoleByChannelAttr(Channel channel){
        Attribute<User> attr = channel.attr(GameContext.CHANNEL_USER_KEY);
        GameRole gameRole = gameContext.getRoles().get(attr.get().getId());
        return gameRole;
    }


    @PostConstruct
    public void init(){
        messageDispatcher.registerService(this);
    }
}
