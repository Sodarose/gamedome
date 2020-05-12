package com.game.service.impl;

import com.game.annotation.CmdHandler;
import com.game.config.MessageType;
import com.game.context.ClientContext;
import com.game.page.PageManager;
import com.game.protocol.Message;
import com.game.protocol.Protocol;
import com.game.service.AbstractGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xuewenkang
 * @date: 2020/5/12 17:05
 */
@Service
public class GameServiceImpl extends AbstractGameService {

    @Autowired
    private ClientContext clientContext;

    @Autowired
    private PageManager pageManager;

    @Override
    public void iniGameClient() {
        requestGameRole();
        requestScene();
    }

    @Override
    public void requestScene() {
        Protocol.Scene scene = Protocol.Scene.newBuilder().build();
        clientContext.getChannel().writeAndFlush(createMessage(MessageType.GAME_SCENE_S,scene.toByteArray()));
    }

    @Override
    public void requestGameRole() {
        Protocol.GameRole gameRole = Protocol.GameRole.newBuilder().build();
        clientContext.getChannel().writeAndFlush(createMessage(MessageType.GAME_ROLE_S,gameRole.toByteArray()));
    }

    @CmdHandler(cmd = MessageType.GAME_SCENE_S)
    @Override
    public void handleSceneMessage(Message message) {

    }

    @CmdHandler(cmd = MessageType.GAME_ROLE_S)
    @Override
    public void handleGameRoleMessage(Message message) {

    }
    private Message createMessage(short cmd,byte[] bytes){
        int length = 6;
        if(bytes!=null){
            length+=bytes.length;
        }
        return  new Message(length,cmd,bytes);
    }

}
