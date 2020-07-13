package com.game.gameserver.module.pet.manager;

import com.game.gameserver.common.config.PetConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.pet.entity.Pet;
import com.game.gameserver.module.pet.entity.UserPet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 宝宝管理
 *
 * @author xuewenkang
 * @date 2020/6/23 9:27
 */
@Component
public class PetManager {
    private final static Logger logger = LoggerFactory.getLogger(PetManager.class);

    private Map<Long, UserPet> playerPetMap = new ConcurrentHashMap<>();
    private Map<Long, Pet> petMap = new ConcurrentHashMap<>();


    public Pet createPet(Long playerId, int petConfigId) {
        PetConfig petConfig = StaticConfigManager.getInstance().getPetConfigMap().get(petConfigId);
        if (petConfig == null) {
            return null;
        }
        UserPet userPet = null;
        if (playerPetMap.get(playerId) == null) {
            userPet = new UserPet();
            playerPetMap.put(playerId, userPet);
        } else {
            userPet = playerPetMap.get(playerId);
        }
        boolean result = userPet.hasPet(petConfigId);
        if (result) {
            logger.info("不能重复召唤");
            return null;
        }
        Pet pet = new Pet(playerId,petConfig);
        pet.initialize();
        userPet.addPet(pet);
        petMap.put(pet.getId(), pet);
        return pet;
    }

    public Pet getPet(Long petId) {
        return petMap.get(petId);
    }

    public void update() {
        for (Map.Entry<Long, Pet> entry : petMap.entrySet()) {
            entry.getValue().update();
        }
    }
}
