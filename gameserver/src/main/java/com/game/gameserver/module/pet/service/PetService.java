package com.game.gameserver.module.pet.service;

import com.game.gameserver.common.config.PetConfig;
import com.game.gameserver.common.config.SkillConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.common.entity.Creature;
import com.game.gameserver.module.pet.model.Pet;
import com.game.gameserver.module.player.model.Player;
import com.game.gameserver.module.skill.model.Skill;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuewenkang
 * @date 2020/6/23 10:10
 */
@Service
public class PetService {
    /**
     * 创建一个召唤物
     *
     * @param player 召唤者
     * @param petConfigId
     * @return com.game.gameserver.module.pet.model.Pet
     */
    public Pet createPet(Player player, int petConfigId){
        PetConfig petConfig = StaticConfigManager.getInstance().getPetConfigMap().get(petConfigId);
        if(petConfig==null){
            return null;
        }
        // 创建一个召唤物
        Pet pet = new Pet(player,petConfig);
        // 加载召唤物技能
        List<Integer> skills = petConfig.getSkills();
        skills.forEach(skillId->{
            SkillConfig skillConfig = StaticConfigManager.getInstance().getSkillConfigMap().get(skillId);
            if(skillConfig!=null){
                Skill skill = new Skill();
                skill.setSkillId(skillId);
                skill.setSkillConfig(skillConfig);
                pet.getSkillMap().put(skillId,skill);
            }
        });
        return pet;
    }

    public void removePet(Pet pet){

    }
}
