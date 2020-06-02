package com.game.gameserver.module.npc.manager;

import com.game.gameserver.dictionary.StaticDataManager;
import com.game.gameserver.dictionary.entity.NpcData;
import com.game.gameserver.module.monster.manager.MonsterManger;
import com.game.gameserver.module.monster.object.MonsterObject;
import com.game.gameserver.module.npc.objcet.NpcObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class NpcManager {
    private final static Logger logger = LoggerFactory.getLogger(NpcManager.class);

    private Map<Integer, NpcObject> monsterObjects = new HashMap<>();
    private AtomicInteger genID = new AtomicInteger(0);

    public NpcObject createNpcObject(int npcId){
        NpcData npcData = StaticDataManager.getInstance().getNpcDict().get(npcId);
        if(npcData==null){
            logger.warn("don't have npc dict by {}",npcId);
            return null;
        }
        NpcObject npcObject = new NpcObject(genID.getAndIncrement(),npcData);
        init(npcObject);
        return npcObject;
    }

    public List<NpcObject> createNpcObjectList(int npcId,int count){
        if(count<=0){
            return null;
        }
        List<NpcObject> npcObjects = new ArrayList<>();
        for(int i=0;i<count;i++){
            NpcObject npcObject = createNpcObject(npcId);
            if(npcObject==null){
                continue;
            }
            npcObjects.add(npcObject);
        }
        return npcObjects;
    }

    private void init(NpcObject npcObject){
        npcObject.init();
    }
}
