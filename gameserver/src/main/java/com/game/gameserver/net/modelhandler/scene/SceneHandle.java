package com.game.gameserver.net.modelhandler.scene;

import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author kangkang
 */
@Component
@ModuleHandler(module = ModuleKey.SCENE_MODULE)
public class SceneHandle extends BaseHandler {

    @Autowired
    private SceneManager sceneManager;

    @CmdHandler(cmd = SceneCmd.CHANGE_SCENE)
    public void handleMoveScene(Message message, Channel channel){

    }
}
