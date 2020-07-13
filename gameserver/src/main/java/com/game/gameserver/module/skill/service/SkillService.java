package com.game.gameserver.module.skill.service;

import com.game.gameserver.module.skill.manager.SkillManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 技能服务
 *
 * @author xuewenkang
 * @date 2020/6/11 10:34
 */
@Service
public class SkillService {

    @Autowired
    private SkillManager skillManager;
}
