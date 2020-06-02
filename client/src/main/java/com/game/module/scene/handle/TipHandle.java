package com.game.module.scene.handle;

import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.TipPage;
import com.game.module.scene.SceneCmd;
import com.game.module.scene.entity.Scene;
import com.game.protocol.Message;
import com.game.protocol.SceneProtocol;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/1 16:02
 */
@Component
@ModuleHandler(module = ModuleKey.TIP_MODEL)
public class TipHandle extends BaseHandler {

    @Autowired
    private TipPage tipPage;

    @CmdHandler(cmd = SceneCmd.SCENE_TIP)
    public void sceneTip(Message message){
        try {
            SceneProtocol.SceneTip sceneTip = SceneProtocol.SceneTip.parseFrom(message.getData());
            tipPage.print(sceneTip.getMsg());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
