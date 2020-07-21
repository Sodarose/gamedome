package com.game.gameserver.module.skill.dao;

import com.game.gameserver.common.db.BaseDbService;
import com.game.gameserver.module.skill.entity.SkillEntity;
import com.game.gameserver.module.skill.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/7/8 11:26
 */
@Repository
public class SkillDbService extends BaseDbService {

    @Autowired
    private SkillMapper skillMapper;

    public List<SkillEntity> skillEntityList(long playerId) {
        return skillMapper.selectSkillList(playerId);
    }

    public SkillEntity select(long skillId, long playerId) {
        return skillMapper.select(skillId,playerId);
    }

    public int update(SkillEntity skillEntity) {
        return skillMapper.update(skillEntity);
    }

    public int insert(SkillEntity skillEntity) {
        return skillMapper.inert(skillEntity);
    }

    public int delete(SkillEntity skillEntity) {
        return skillMapper.delete(skillEntity);
    }

    public void updateAsync(SkillEntity skillEntity) {
        submit(() -> {
            update(skillEntity);
        });
    }

    public void insertAsync(SkillEntity skillEntity) {
        submit(() -> {
            insert(skillEntity);
        });
    }

    public void deleteAsync(SkillEntity skillEntity) {
        submit(() -> {
            delete(skillEntity);
        });
    }
}
