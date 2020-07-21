package com.game.gameserver.module.skill.model;

import com.game.gameserver.module.skill.entity.SkillEntity;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 角色技能容器
 *
 * @author xuewenkang
 * @date 2020/6/10 10:31
 */
@Data
public class PlayerSkill {
    /** 玩家 */
    private long playerId;
    /** 以技能资源id为键 */
    private  Map<Integer, Skill> skillMap;

    public PlayerSkill(long playerId){
        this.playerId = playerId;
        this.skillMap = new HashMap<>();
    }

}
