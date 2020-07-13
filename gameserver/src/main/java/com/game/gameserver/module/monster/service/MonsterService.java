package com.game.gameserver.module.monster.service;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.monster.model.Monster;
import org.springframework.stereotype.Service;

/**
 * @author xuewenkang
 * @date 2020/7/11 18:46
 */
@Service
public class MonsterService {

    public Monster createScene(int monsterId){
        // 获取配置
        MonsterConfig monsterConfig = StaticConfigManager.getInstance().getMonsterConfigMap().get(monsterId);
        // 根据配置创建怪物
        return new Monster(monsterConfig);
    }

}
