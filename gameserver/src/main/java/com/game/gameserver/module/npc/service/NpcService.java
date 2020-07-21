package com.game.gameserver.module.npc.service;

import com.game.gameserver.common.config.NpcConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.event.EventBus;
import com.game.gameserver.event.event.TalkEvent;
import com.game.gameserver.module.notification.NotificationHelper;
import com.game.gameserver.module.npc.model.Npc;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.scene.model.GameScene;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author xuewenkang
 * @date 2020/7/11 16:08
 */
@Service
public class NpcService {

    public Npc createNpc(int npcConfigId){
        NpcConfig npcConfig = StaticConfigManager.getInstance().getNpcConfigMap().get(npcConfigId);
        return new Npc(npcConfig);
    }

    public void talkNpc(Player player, long npcId){
        GameScene currScene = (GameScene) player.getCurrScene();
        Npc npc = currScene.getNpcMap().get(npcId);
        if(npc==null){
            NotificationHelper.notifyPlayer(player,"此场景内无此NPC");
            return;
        }
        NotificationHelper.notifyPlayer(player,
                MessageFormat.format("{0}:{1}",npc.getName(),npc.getTalk()));
        EventBus.EVENT_BUS.fire(new TalkEvent(player,npc));
    }
}
