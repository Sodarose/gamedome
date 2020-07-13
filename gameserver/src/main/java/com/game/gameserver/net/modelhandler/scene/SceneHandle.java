package com.game.gameserver.net.modelhandler.scene;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.scene.manager.SceneManager;
import com.game.gameserver.module.scene.service.SceneService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.protocol.Message;
import com.game.protocol.SceneProtocol;
import com.game.util.MessageUtil;
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
    private SceneService sceneService;

    @CmdHandler(cmd = SceneCmd.MOVE_SCENE)
    public void handleMoveSceneReq(Message message, Channel channel) {

    }

    @CmdHandler(cmd = SceneCmd.QUERY_SCENE_LIST)
    public void handleQuerySceneListReq(Message message, Channel channel) {
        Player player = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(player==null){
            return;
        }
        SceneProtocol.QuerySceneListRes res = sceneService.querySceneList();
        Message resMsg = MessageUtil.createMessage(ModuleKey.SCENE_MODULE,SceneCmd.QUERY_SCENE_LIST,
                res.toByteArray());
        channel.writeAndFlush(resMsg);
    }
}
