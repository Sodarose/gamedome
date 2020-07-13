package com.game.gameserver.net.modelhandler.skill;

import com.game.gameserver.module.skill.service.SkillService;
import com.game.gameserver.net.annotation.ModuleHandler;
import com.game.gameserver.net.handler.BaseHandler;
import com.game.gameserver.net.modelhandler.ModuleKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuewenkang
 * @date 2020/6/4 20:34
 */
@Component
@ModuleHandler(module = ModuleKey.SKILL_MODULE)
public class SkillHandle extends BaseHandler {
    @Autowired
    private SkillService skillService;
}
