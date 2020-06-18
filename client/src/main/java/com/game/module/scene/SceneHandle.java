package com.game.module.scene;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.ScenePage;
import com.game.module.player.OtherPlayerInfo;
import com.game.module.scene.SceneCmd;
import com.game.protocol.Message;
import com.game.protocol.SceneProtocol;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.game.util.TransFromUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/1 10:19
 */
@Component
@ModuleHandler(module = ModuleKey.SCENE_MODULE)
public class SceneHandle extends BaseHandler {

    private SceneInfo sceneInfo;

    @Autowired
    private ScenePage scenePage;
    @Autowired
    private ClientGameContext gameContext;


    /**
     * @param
     * @return void
     */
    public void requestSceneInfo() {
        Message message = MessageUtil.createMessage(ModuleKey.SCENE_MODULE, SceneCmd.SCENE_INFO_REQ, null);
    }

    /**
     * 同步场景数据
     *
     * @param message
     * @return void
     */
    @CmdHandler(cmd = SceneCmd.SYNC_SCENE)
    public void syncScene(Message message) {
        try {
            SceneProtocol.SceneInfo info = SceneProtocol.SceneInfo.parseFrom(message.getData());
            SceneInfo sceneInfo = TransFromUtil.transFromSceneInfo(info);
            this.sceneInfo = sceneInfo;
            scenePage.clean();
            scenePage.printSceneInfo(sceneInfo);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public OtherPlayerInfo getOtherPlayerInfoByName(String name) {
        if (sceneInfo == null) {
            return null;
        }
        Map<Long, OtherPlayerInfo> map = sceneInfo.getPlayerMap();
        for (Map.Entry<Long, OtherPlayerInfo> entry : map.entrySet()) {
            if(entry.getValue().getName().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }
}
