package com.game.gameserver.module.instance.manager;

import com.game.gameserver.common.config.*;
import com.game.gameserver.module.instance.model.InstanceObject;
import com.game.gameserver.module.monster.manager.MonsterManager;
import com.game.gameserver.module.npc.manager.NpcManager;
import com.game.gameserver.module.player.entity.Player;
import com.game.gameserver.module.team.manager.TeamManager;
import com.game.gameserver.module.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private Map<Long, InstanceObject> instanceObjectMap = new ConcurrentHashMap<>(1);

    @Autowired
    private TeamManager teamManager;
    @Autowired
    private MonsterManager monsterManager;
    @Autowired
    private NpcManager npcManager;

    /**
     * 单人进入副本
     *
     * @param player
     * @return void
     */
    public void entryInstanceByOnce(Player player){

    }


    /**
     * 组队进入
     *
     * @param team
     * @return void
     */
    public void entryInstanceByTeam(Team team){

    }



    /** 创建一个副本 */
    public InstanceObject createInstanceObject(int instanceId){
        InstanceConfig instanceConfig = StaticConfigManager.getInstance().getInstanceConfigMap().get(instanceId);
        if(instanceConfig==null){
            return null;
        }
        return null;
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
