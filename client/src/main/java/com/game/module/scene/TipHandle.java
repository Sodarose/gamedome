package com.game.module.scene;

import com.game.module.BaseHandler;
import com.game.module.ModuleKey;
import com.game.module.gui.TipPage;
import com.game.task.annotation.ModuleHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/1 16:02
 */
@Component
@ModuleHandler(module = ModuleKey.FIGHTER_MODEL)
public class TipHandle extends BaseHandler {

    @Autowired
    private TipPage tipPage;

}
