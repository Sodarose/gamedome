package com.game.gameserver.module.monster.manager;

import com.game.gameserver.module.monster.object.MonsterObject;
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
    private Map<Integer, MonsterObject> monsterProjectMap = new HashMap<>();
    private AtomicInteger genID = new AtomicInteger(0);

    public List<MonsterObject> createMonsterProject(int count, int sceneId){
        List<MonsterObject> monsterObjects = new ArrayList<>();
        for(int i=0;i<count;i++){

        }
        return monsterObjects;
    }


}
