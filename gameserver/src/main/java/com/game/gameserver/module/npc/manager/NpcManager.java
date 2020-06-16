package com.game.gameserver.module.npc.manager;

import com.game.gameserver.common.config.NpcConfig;
import com.game.gameserver.common.config.StaticConfigManager;
import com.game.gameserver.module.npc.model.NpcObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuewenkang
 * @date 2020/6/9 11:42
 */
@Component
public class NpcManager {
    private final static Logger logger = LoggerFactory.getLogger(NpcManager.class);

    public static NpcManager instance;

    public NpcManager(){
        instance = this;
    }

    /** npc 表 */
    private final Map<Long, NpcObject> npcObjectMap = new ConcurrentHashMap<>();

    public NpcObject createNpcObject(int npcId) {
        NpcConfig npcConfig = StaticConfigManager.getInstance().getNpcConfigMap().get(npcId);
        if (npcConfig == null) {
            return null;
        }
        NpcObject npcObject = new NpcObject(npcConfig);
        npcObject.initialize();
        npcObjectMap.put(npcObject.getId(), npcObject);
        return npcObject;
    }

    public List<NpcObject> createNpcObjectList(int npcId, int count) {
        List<NpcObject> npcObjectList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NpcObject npcObject = createNpcObject(npcId);
            if (npcObject == null) {
                continue;
            }
            npcObjectList.add(npcObject);
        }
        return npcObjectList;
    }
}
