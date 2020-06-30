package com.game.module.scene;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.ScenePage;
import com.game.module.gui.WordPage;
import com.game.module.monster.Monster;
import com.game.module.player.OtherPlayerInfo;
import com.game.module.scene.SceneCmd;
import com.game.protocol.Actor;
import com.game.protocol.Message;
import com.game.protocol.SceneProtocol;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import com.game.util.MessageUtil;
import com.game.util.TransFromUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuewenkang
 * @date 2020/6/1 10:19
 */
@Component
@ModuleHandler(module = ModuleKey.SCENE_MODULE)
public class SceneHandle extends BaseHandler {


    @Autowired
    private ScenePage scenePage;
    @Autowired
    private ClientGameContext gameContext;

    private SceneProtocol.SceneInfo currSceneInfo;
    @Autowired
    private WordPage wordPage;

    public void sceneAio(){

    }

    /**
     * 同步场景数据
     *
     * @param message
     * @return void
     */
    @CmdHandler(cmd = SceneCmd.SYNC_SCENE)
    public void syncScene(Message message) {

    }

}
