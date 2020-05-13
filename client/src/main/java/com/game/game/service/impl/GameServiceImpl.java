package com.game.game.service.impl;

import com.game.config.MessageType;
import com.game.game.page.WordPage;
import com.game.game.context.GameContext;
import com.game.task.MessageDispatcher;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.game.game.service.AbstractGameService;
import com.game.task.annotation.CmdHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 17:05
 * 接受数据 并 渲染页面
 */
@Service
public class GameServiceImpl extends AbstractGameService {

    private final static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private GameContext gameContext;

    @Autowired
    private WordPage wordPage;

    @CmdHandler(cmd = MessageType.GAME_SCENE_S)
    @Override
    public void handleSceneMessage(Message message) {
        logger.info("handle scene message ");
        try {
            Protocol.Scene scene = Protocol.Scene.parseFrom(message.getData());
            gameContext.setScene(scene);
            gameContext.setWinScene(scene);
            wordPage.print(scene);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            logger.error("parse scene message error");
        }
    }

    @CmdHandler(cmd = MessageType.GAME_ROLE_S)
    @Override
    public void handleRoleMessage(Message message) {
        logger.info("handle role message");
        try{
            Protocol.Role role = Protocol.Role.parseFrom(message.getData());
            wordPage.print(role);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = MessageType.GAME_CUT_S)
    @Override
    public void handleCutSceneMessage(Message message) {
        try {
            Protocol.Scene scene = Protocol.Scene.parseFrom(message.getData());
            gameContext.setWinScene(scene);
            wordPage.print("查看场景:");
            wordPage.print(scene);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @CmdHandler(cmd = MessageType.GAME_MOVE_S)
    @Override
    public void handleMoveSceneMessage(Message message) {
        try {
            Protocol.Scene scene = Protocol.Scene.parseFrom(message.getData());
            gameContext.setScene(scene);
            gameContext.setWinScene(scene);
            wordPage.print("玩家移动到:");
            wordPage.print(scene);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    @PostConstruct
    public void init() {
        messageDispatcher.registerService(this);
    }

}
