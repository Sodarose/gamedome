package com.game.gameserver.module.skill.model;

import com.game.gameserver.common.config.SkillConfig;
import com.game.gameserver.module.skill.entity.SkillEntity;
import com.game.gameserver.net.modelhandler.skill.SkillCmd;
import lombok.Data;

/**
 * @author xuewenkang
 * @date 2020/7/16 15:13
 */
@Data
public class Skill extends SkillEntity {
    private SkillConfig skillConfig;

    public int getConsume(){
        return skillConfig.getConsume();
    }

    public int getRange(){
        return skillConfig.getRange();
    }

    public int getType(){
        return skillConfig.getType();
    }
}
