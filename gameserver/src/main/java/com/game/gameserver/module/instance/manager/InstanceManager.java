package com.game.gameserver.module.instance.manager;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.instance.model.InstanceInfo;
import com.game.gameserver.module.instance.model.InstanceObject;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.npc.manager.NpcManager;
import com.game.gameserver.module.player.model.PlayerObject;
import com.game.gameserver.module.team.manager.TeamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 副本管理器
 *
 * @author xuewenkang
 * @date 2020/6/8 18:29
 */
@Component
public class InstanceManager {
    /** 已经创建的副本对象 */
    private Map<Integer, InstanceObject> instanceObjectMap = new ConcurrentHashMap<>(1);
    /** 副本信息 */
    private Map<Integer,InstanceInfo> instanceInfoMap = new ConcurrentHashMap<>(1);

    @Autowired
    private TeamManager teamManager;
    @Autowired
    private MonsterManager monsterManager;
    @Autowired
    private NpcManager npcManager;

    /** 创建一个副本 */
    public InstanceObject createInstanceObject(PlayerObject playerObject,int instanceId){
        InstanceConfig instanceConfig = StaticConfigManager.getInstance().getInstanceConfigMap().get(instanceId);
        if(instanceConfig==null){
            return null;
        }
        // 获取副本怪物配置
        InstanceMonsterConfig instanceMonsterConfig = StaticConfigManager.getInstance().getInstanceMonsterConfigMap()
                .get(instanceConfig.getMonsterConfig());
        // 获取副本Npc配置
        InstanceNpcConfig instanceNpcConfig = StaticConfigManager.getInstance().getInstanceNpcConfigMap()
                .get(instanceConfig.getNpcConfig());
        // 创建副本
        InstanceObject instanceObject = new InstanceObject(instanceConfig,instanceMonsterConfig,instanceNpcConfig);
        // 初始化
        instanceObject.initialize();
        instanceObjectMap.put(instanceObject.getId(),instanceObject);
        return instanceObject;
    }



    /**
     * 移除副本
     *
     * @param instanceId
     * @return void
     */
    public void removeInstanceObject(int instanceId){
        instanceObjectMap.remove(instanceId);
    }
}
