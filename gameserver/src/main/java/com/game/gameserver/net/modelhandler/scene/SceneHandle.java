package com.game.gameserver.net.modelhandler.scene;

import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.npc.service.NpcService;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.player.service.PlayerService;
import com.game.gameserver.module.scene.service.SceneService;
import com.game.gameserver.net.annotation.CmdHandler;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.message.Message;
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
    @Autowired
    private NpcService npcService;

    @CmdHandler(cmd = SceneCmd.SHOW_SCENE)
    public void showScene(Message message, Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerDomain==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        sceneService.showScene(playerDomain);
    }

    @CmdHandler(cmd = SceneCmd.MOVE_SCENE)
    public void moveScene(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerDomain==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        if(message.getContent()==null||message.getContent().isEmpty()){
            NotificationHelper.notifyChannel(channel,"参数错误");
            return;
        }
        int sceneId = Integer.parseInt(message.getContent());
        sceneService.moveScene(playerDomain,sceneId);
    }

    @CmdHandler(cmd = SceneCmd.AIO)
    public void aio(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerDomain==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        sceneService.aio(playerDomain);
    }

    @CmdHandler(cmd = SceneCmd.SCENE_LIST)
    public void sceneList(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerDomain==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        sceneService.sceneList(playerDomain);
    }

    @CmdHandler(cmd = SceneCmd.CHECK_SCENE)
    public void checkScene(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerDomain==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        int sceneId = Integer.parseInt(message.getContent());
        sceneService.checkScene(playerDomain,sceneId);
    }

    @CmdHandler(cmd = SceneCmd.TALK_NPC)
    public void talkNpc(Message message,Channel channel){
        Player playerDomain = channel.attr(PlayerService.PLAYER_ENTITY_ATTRIBUTE_KEY).get();
        if(playerDomain==null){
            NotificationHelper.notifyChannel(channel,"请先登录角色");
            return;
        }
        long npcId = Long.parseLong(message.getContent());
        npcService.talkNpc(playerDomain,npcId);
    }
}
