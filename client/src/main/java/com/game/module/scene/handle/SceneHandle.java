package com.game.module.scene.handle;

import com.game.context.ClientGameContext;
import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.ScenePage;
import com.game.module.scene.SceneCmd;
import com.game.protocol.Message;
import com.game.task.annotation.CmdHandler;
import com.game.task.annotation.ModuleHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/1 10:19
 */
@Component
@ModuleHandler(module = ModuleKey.SCENE_MODEL)
public class SceneHandle extends BaseHandler {

    @Autowired
    private ScenePage scenePage;
    @Autowired
    private ClientGameContext gameContext;

    /**
     * 同步场景数据
     *
     * @param message
     * @return void
     */
    @CmdHandler(cmd = SceneCmd.SYNC_SCENE)
    public void syncScene(Message message){

    }
}
