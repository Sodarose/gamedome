package com.game.gameserver.module.scene.helper;

import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.scene.model.Scene;
import com.game.gameserver.net.modelhandler.ModuleKey;
import com.game.gameserver.net.modelhandler.scene.SceneCmd;
import com.game.gameserver.util.ProtocolFactory;
import com.game.protocol.Message;
import com.game.protocol.SceneProtocol;
import com.game.util.MessageUtil;

import java.util.List;

/**
 * 场景助手  负责广播/同步场景信息/
 *
 * @author xuewenkang
 * @date 2020/6/30 20:49
 */
public class SceneHelper {
    /** 同步场景数据 */
    public static void syncSceneInfo(Scene scene){
        // 获取场景内角色列表
        List<Player> players = scene.getPlayers();
        // 生成场景信息
        SceneProtocol.SceneInfo  sceneInfo = ProtocolFactory.createSceneInfo(scene);
        // 构建同步消息
        Message syncMsg = MessageUtil.createMessage(ModuleKey.SCENE_MODULE, SceneCmd.SYNC_SCENE
                , sceneInfo.toByteArray());
        // 广播消息
        for(Player player :players){
            player.getChannel().writeAndFlush(syncMsg);
        }
    }
}
