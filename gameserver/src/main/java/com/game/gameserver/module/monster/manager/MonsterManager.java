package com.game.gameserver.module.monster.manager;

import com.game.gameserver.common.config.MonsterConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.event.EventHandler;
import com.game.gameserver.event.EventType;
import com.game.gameserver.event.Listener;
import com.game.gameserver.module.monster.event.MonsterDeadEvent;
import com.game.gameserver.module.monster.model.MonsterObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 怪物管理类
 *
 * @author xuewenkang
 * @date 2020/6/9 11:33
 */
@Component
@Listener
public class MonsterManager {

    public static MonsterManager instance;

    public MonsterManager() {
        instance = this;
    }

    private final static Logger logger = LoggerFactory.getLogger(MonsterManager.class);
    private final Map<Long, MonsterObject> monsterObjectMap = new ConcurrentHashMap<>(1);

    public MonsterObject createMonsterObject(int monsterId, int monsterType, Long addrId) {
        logger.info("create Monster by monsterId {}", monsterId);
        MonsterConfig monsterConfig = StaticConfigManager.getInstance().getMonsterConfigMap().get(monsterId);
        if (monsterConfig == null) {
            return null;
        }
        MonsterObject monsterObject = new MonsterObject(monsterConfig, monsterType, addrId);
        monsterObject.initialize();
        monsterObjectMap.put(monsterObject.getId(), monsterObject);
        return monsterObject;
    }

    public List<Long> createMonsterObjectList(int monsterId, int count, int monsterType, Long addrId) {
        List<Long> monsterList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MonsterObject monsterObject = createMonsterObject(monsterId, monsterType, addrId);
            if (monsterObject == null) {
                continue;
            }
            monsterList.add(monsterObject.getId());
        }
        return monsterList;
    }


    public MonsterObject getMonster(Long monsterId) {
        return monsterObjectMap.get(monsterId);
    }

    public void removeMonster(Long monsterId) {
        monsterObjectMap.remove(monsterId);
    }


    /**
     * 处理怪物死亡事件
     *
     * @param event
     * @return void
     */
    @EventHandler(type = EventType.MONSTER_DEAD)
    public void handleMonsterDeadEvent(MonsterDeadEvent event) {

    }
}
