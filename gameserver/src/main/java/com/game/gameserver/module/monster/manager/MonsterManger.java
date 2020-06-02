package com.game.gameserver.module.monster.manager;

import com.game.gameserver.dictionary.StaticDataManager;
import com.game.gameserver.dictionary.entity.MonsterData;
import com.game.gameserver.module.monster.entity.MonsterEntity;
import com.game.gameserver.module.monster.object.MonsterObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuewenkang
 * @date 2020/6/1 18:20
 */
@Component
public class MonsterManger {
    private final static Logger logger = LoggerFactory.getLogger(MonsterManger.class);

    private Map<Integer, MonsterObject> monsterObjects = new HashMap<>();
    private AtomicInteger genID = new AtomicInteger(0);

    public MonsterObject createMonsterObject(int monsterId){
        logger.info("create a monsterObject by monster type is {}",monsterId);
        MonsterData monsterData = StaticDataManager.getInstance().getMonsterDict().get(monsterId);
        if(monsterData==null){
            logger.warn("don't have monster dict by {}",monsterId);
            return null;
        }
        MonsterObject monsterObject = new MonsterObject(genID.getAndIncrement(),monsterData);
        init(monsterObject);
        monsterObjects.put(monsterObject.getId(),monsterObject);
        return monsterObject;
    }

    public List<MonsterObject> createMonsterObjectList(int monsterId,int count){
        if(count<=0){
            return null;
        }
        List<MonsterObject> monsterObjects = new ArrayList<>();
        for(int i=0;i<count;i++){
            MonsterObject monsterObject = createMonsterObject(monsterId);
            if(monsterObject==null){
                continue;
            }
            monsterObjects.add(monsterObject);
        }
        return monsterObjects;
    }

    private void init(MonsterObject monsterObject){
        monsterObject.init();
    }
}
