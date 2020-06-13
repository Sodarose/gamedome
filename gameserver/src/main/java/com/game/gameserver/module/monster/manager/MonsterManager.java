package com.game.gameserver.module.monster.manager;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.monster.model.MonsterObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/9 11:33
 */
@Component
public class MonsterManager {

    public static MonsterManager instance;

    public MonsterManager() {
        instance = this;
    }

    private final static Logger logger = LoggerFactory.getLogger(MonsterManager.class);
    private final Map<Integer, MonsterObject> monsterObjectMap = new ConcurrentHashMap<>(1);

    public MonsterObject createMonsterObject(int monsterId) {
        logger.info("create Monster by monsterId {}", monsterId);
        MonsterConfig monsterConfig = StaticConfigManager.getInstance().getMonsterConfigMap().get(monsterId);
        if (monsterConfig == null) {
            return null;
        }
        MonsterObject monsterObject = new MonsterObject(monsterConfig);
        monsterObject.initialize();
        monsterObjectMap.put(monsterObject.getId(), monsterObject);
        return monsterObject;
    }

    public List<MonsterObject> createMonsterObjectList(int monsterId, int count) {
        List<MonsterObject> monsterObjectList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MonsterObject monsterObject = createMonsterObject(monsterId);
            if (monsterObject == null) {
                continue;
            }
            monsterObjectList.add(monsterObject);
        }
        return monsterObjectList;
    }

    public static MonsterManager getInstance(){
        return instance;
    }
}
