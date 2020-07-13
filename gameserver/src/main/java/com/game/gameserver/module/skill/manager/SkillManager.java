package com.game.gameserver.module.skill.manager;

import com.game.gameserver.event.Listener;
import com.game.gameserver.module.player.manager.PlayerManager;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.skill.dao.SkillMapper;
import com.game.gameserver.module.skill.entity.PlayerSkill;
import com.game.gameserver.module.skill.entity.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 技能管理器
 *
 * @author xuewenkang
 * @date 2020/6/17 16:48
 */
@Listener
@Component
public class SkillManager {
    private final static Logger logger = LoggerFactory.getLogger(SkillManager.class);
    private final Map<Long, PlayerSkill> skillMap = new ConcurrentHashMap<>();

    @Autowired
    private SkillMapper skillMapper;
    @Autowired
    private PlayerManager playerManager;

    /**
     * 从数据库中读取玩家技能列表
     *
     * @param playerId
     * @return void
     */
    private void loadPlayerSkill(long playerId) {
        Player player = playerManager.getPlayer(playerId);
        if (player == null) {
            return;
        }
        // 从数据库中读取用户技能数据
        List<Skill> skills = skillMapper.getSkillList(playerId);
        // 创建用户技能容器
        PlayerSkill playerSkill = new PlayerSkill(playerId);
        // 初始化
        playerSkill.initialize(skills);
        // 放入map中
        skillMap.put(playerId, playerSkill);
    }

    public void putPlayerSkill(long playerId, PlayerSkill playerSkill) {
        this.skillMap.put(playerId, playerSkill);
    }

    public void removePlayerSkill(long playerId) {
        this.skillMap.remove(playerId);
    }

    public PlayerSkill getPlayerSkill(long playerId){
        return skillMap.get(playerId);
    }
}
