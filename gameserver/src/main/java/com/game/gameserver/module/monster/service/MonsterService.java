package com.game.gameserver.module.monster.service;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.config.SkillConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.fsm.StateMachine;
import com.game.gameserver.common.fsm.state.monster.MonsterState;
import com.game.gameserver.module.monster.model.Monster;
import com.game.gameserver.module.skill.model.Skill;
import com.game.gameserver.util.GameUUID;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/11 18:46
 */
@Service
public class MonsterService {

    public Monster createMonster(int monsterId){
        // 获取配置
        MonsterConfig monsterConfig = StaticConfigManager.getInstance().getMonsterConfigMap().get(monsterId);
        // 根据配置创建怪物
        Monster monster = new Monster();
        monster.setId(GameUUID.getInstance().generate());
        monster.setName(monsterConfig.getName());
        monster.setLevel(monsterConfig.getLevel());
        monster.setType(monsterConfig.getType());
        monster.setHp(monsterConfig.getHp());
        monster.setCurrHp(monsterConfig.getHp());
        monster.setMp(monsterConfig.getMp());
        monster.setCurrMp(monsterConfig.getMp());
        monster.setAttack(monsterConfig.getAttack());
        monster.setDefense(monsterConfig.getDefense());
        monster.setRefreshTime(monsterConfig.getRefreshTime());
        monster.setTarget(null);
        monster.setState(MonsterState.MONSTER_DEFEND);
        monster.setStateMachine(new StateMachine<>(monster,MonsterState.MONSTER_DEFEND));

        // 加载怪物技能
        List<Integer> skills = monsterConfig.getSkills();
        skills.forEach(skillId->{
            SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skillId);
            if(skillConfig!=null){
                Skill skill = new Skill();
                skill.setSkillId(skillId);
                skill.setSkillConfig(skillConfig);
                monster.getSkillMap().put(skillId,skill);
            }
        });
        return monster;
    }

}
